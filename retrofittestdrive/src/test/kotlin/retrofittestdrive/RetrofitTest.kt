package retrofittestdrive

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.matchers.TimeToLive
import org.mockserver.matchers.Times
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import retrofit2.Call
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS


internal class RetrofitTest {

    companion object {
        private const val mockServerPort = 1080
        private lateinit var mockServer: ClientAndServer

        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            mockServer = startClientAndServer(mockServerPort)
            println("XXX MockServer is up: ${mockServer.isRunning} on port: ${mockServer.port}")

            mockServer.`when`(
                request().withMethod("GET").withPath("/users/octocat/repos")
            ).respond(
                response()
                    .withStatusCode(200)
                    .withBody(""" [{ "id": 1296269, "name": "octocat", "owner": {} }] """.trimIndent())
            )

            // ######################################

            mockServer.`when`(
                request().withMethod("GET").withPath("/users/good"),
            ).respond(
                response()
                    .withStatusCode(200)
                    .withBody("""[{ "id": 1296269, "name": "good", "owner": {"login": "good"} }]""")
            )

            // quirky - use priority to simulate x2 500 errors followed by 200
            mockServer.`when`(
                request()
                    .withMethod("GET").withPath("/users/quirky"),
                Times.exactly(2), TimeToLive.exactly(SECONDS, 60L), 100
            ).respond(
                response()
                    .withStatusCode(500)
            )

            mockServer.`when`(
                request()
                    .withMethod("GET").withPath("/users/quirky"),
                Times.exactly(2), TimeToLive.exactly(SECONDS, 60L), 1
            ).respond(
                response()
                    .withStatusCode(200)
                    .withBody("""[{ "id": 1296269, "name": "quirky", "owner": {"login": "quirky"} }]""")
            )
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            mockServer.stop()
        }
    }

    private val baselineClient = buildBaselineClient(GitHubApi::class.java, "http://localhost:1080/")
    private val resilientClientX = buildResilientClient(LocalTestApi::class.java, "http://localhost:1080/")

    @Test
    fun `baseline - matching requests = 200`() {
        val call: Call<List<Repo>> = baselineClient.listRepos("octocat")
        val response = call.execute()
        val result: List<Repo>? = response.body()

        assertEquals(200, response.code())
        assertEquals(1, result!!.size)
    }

    @Test
    fun `baseline - non matching requests = 404`() {
        baselineClient.listRepos("bad cat!").execute().also { response ->
            assertEquals(404, response.code())
        }
    }

// #################################################

    @Test
    fun `resilient - 200`() {
        resilientClientX.getLocal(id = "good").execute().also { response ->
            assertEquals(200, response.code())
            assertEquals("good", response.body()!![0].name)
        }
    }

    @Test
    fun `resilient - 404`() {
        resilientClientX.getLocal(id = "not good").execute().also { response ->
            assertEquals(404, response.code())
//            assertEquals("not good", mockServer.verify(request().withPath("/users/quirky"), exactly(1)))

        }
    }

    @Test
    fun `resilient - quirky`() {
        resilientClientX.getLocal(id = "quirky").execute().also { response ->
            assertEquals(200, response.code())
            assertEquals("quirky", response.body()!![0].name)
        }
    }


}
