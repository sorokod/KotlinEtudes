package retrofittestdrive

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.matchers.TimeToLive
import org.mockserver.matchers.Times
import org.mockserver.model.HttpError.error
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.verify.VerificationTimes.*
import retrofit2.Call
import java.util.concurrent.TimeUnit.SECONDS


internal class RetrofitTest {

    companion object {
        private const val mockServerPort = 1080
        private lateinit var mockServer: ClientAndServer

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            mockServer = startClientAndServer(mockServerPort)
            println("### MockServer is up: ${mockServer.isRunning} Port: ${mockServer.port}")
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            mockServer.stop()
        }
    }

    private val baselineClient = buildBaselineClient(GitHubApi::class.java, "http://localhost:1080/")
    private val resilientClient = buildResilientClient(LocalTestApi::class.java, "http://localhost:1080/")

    @Test
    fun `baseline - matching requests = 200`() {
        mockServer.scenario_Octocat_200()

        val call: Call<List<Repo>> = baselineClient.listRepos("octocat")
        val response = call.execute()
        val result: List<Repo> = response.body()!!

        assertEquals(200, response.code())
        assertEquals(1, result.size)
    }

    @Test
    fun `baseline - non matching requests = 404`() {
        mockServer.scenario_Octocat_200()

        baselineClient.listRepos("bad cat!").execute().also { response ->
            assertEquals(404, response.code())
        }
    }

    @Test
    fun `resilient - 200`() {
        val id = "good"
        mockServer.scenario_200(id)

        resilientClient.getLocal(id).execute().also { response ->
            assertEquals(200, response.code())
            assertEquals(id, response.body()!![0].name)
        }
    }

    /**
     * No retries as a result of 404
     */
    @Test
    fun `resilient - 404 - fail after one attempt`() {
        val id = "not_good"
        mockServer.scenario_200("good")

        resilientClient.getLocal(id).execute().also { response ->
            assertEquals(404, response.code())
            mockServer.verify(request().withPath("/users/$id"), exactly(1))
        }
    }

    /**
     * Retry 3 times failing twice due to 500 error, succeed
     * the third time when 200 is returned
     */
    @Test
    fun `resilient - 500 - fail twice succeed on third`() {
        val id = "quirky"
        mockServer.scenario_500(id)

        val start = System.currentTimeMillis()
        resilientClient.getLocal(id).execute().also { response ->
            assertEquals(200, response.code())
            assertEquals("finally_$id", response.body()!![0].name)
            mockServer.verify(request().withPath("/users/$id"), exactly(3))
        }
        println("XXX DOne in (${System.currentTimeMillis() - start})")
    }

    /**
     * Retry 3 times failing twice due to an exception succeed
     * the third time when 200 is returned
     */
    @Test
    fun `resilient - exception - fail twice succeed on third`() {
        val id = "quirky"
        mockServer.scenario_exception(id)

        resilientClient.getLocal(id).execute().also { response ->
            assertEquals(200, response.code())
            assertEquals("exception_$id", response.body()!![0].name)
            mockServer.verify(request().withPath("/users/$id"), exactly(3))
        }
    }

}

// ###########################################


fun ClientAndServer.scenario_Octocat_200() {
    this.reset()
    this.`when`(
        request().withMethod("GET").withPath("/users/octocat/repos")
    ).respond(
        response().withStatusCode(200)
            .withBody("""[{ "id": 1296269, "name": "octocat", "owner": {}}]""")
    )
}

fun ClientAndServer.scenario_200(id: String) {
    this.reset()
    this.`when`(getForId(id))
        .respond(
            response().withStatusCode(200)
                .withBody("""[{ "id": 1296269, "name": "$id", "owner": {}}]""")
        )
}

fun ClientAndServer.scenario_500(id: String) {
    this.reset()
    // use priority to simulate x2 500 errors followed by 200
    this.`when`(getForId(id), Times.exactly(2), TimeToLive.exactly(SECONDS, 60L), 100)
        .respond(
            response().withStatusCode(500)
        )

    this.`when`(getForId(id), Times.exactly(1), TimeToLive.exactly(SECONDS, 60L), 1)
        .respond(
            response().withStatusCode(200)
                .withBody("""[{ "id": 1296269, "name": "finally_$id", "owner": {}}]""")
        )
}

fun ClientAndServer.scenario_exception(id: String) {
    this.reset()
    // quirky - use priority to simulate x2 drop connection errors followed by 200
    this.`when`(
        getForId(id), Times.exactly(2), TimeToLive.exactly(SECONDS, 60L), 100
    ).error(error().withDropConnection(true))

    this.`when`(
        getForId(id), Times.exactly(1), TimeToLive.exactly(SECONDS, 60L), 1)
        .respond(
            response().withStatusCode(200)
                .withBody("""[{ "id": 1296269, "name": "exception_$id", "owner": {}}]""")
        )
}

fun getForId(id: String): HttpRequest =
    HttpRequest().withMethod("GET").withPath("/users/${id}")

