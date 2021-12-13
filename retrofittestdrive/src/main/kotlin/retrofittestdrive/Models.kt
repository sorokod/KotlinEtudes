package retrofittestdrive

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


data class Repo(
    val id: String,
    val name: String,
    val owner: Map<String, String>
)

/**
 * For test-driving retrofit2
 * See https://docs.github.com/en/rest/reference/repos#list-repositories-for-a-user
 */
interface GitHubApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>
}

/**
 * Local test API - used for testing with added resilience4j
 * configuration.
 */
interface LocalTestApi {
    @GET("users/{id}")
    fun getLocal(@Path("id") id: String): Call<List<Repo>>
}
