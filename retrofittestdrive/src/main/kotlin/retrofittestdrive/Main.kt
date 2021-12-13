package retrofittestdrive

import retrofit2.Call


fun main() {
    val client = buildBaselineClient(GitHubApi::class.java, "https://api.github.com/")
    val call: Call<List<Repo>> = client.listRepos("octocat")
    val result: List<Repo>? = call.execute().body()


    result?.forEachIndexed { i, repo ->
        println("$i) $repo")
    } ?: println("XXX Result was null")

// prints:
//    GitHubApi.listRepos([octocat]) - 200 in 411 ms .
//    0) Repo(id=132935648, name=boysenberry-repo-1, owner={login=octocat, id=583231, node_id=MDQ6VXNlcjU4MzIzMQ==, avatar_url=https://avatars.githubusercontent.com/u/583231?v=4, gravatar_id=, url=https://api.github.com/users/octocat, html_url=https://github.com/octocat, followers_url=https://api.github.com/users/octocat/followers, following_url=https://api.github.com/users/octocat/following{/other_user}, gists_url=https://api.github.com/users/octocat/gists{/gist_id}, starred_url=https://api.github.com/users/octocat/starred{/owner}{/repo}, subscriptions_url=https://api.github.com/users/octocat/subscriptions, organizations_url=https://api.github.com/users/octocat/orgs, repos_url=https://api.github.com/users/octocat/repos, events_url=https://api.github.com/users/octocat/events{/privacy}, received_events_url=https://api.github.com/users/octocat/received_events, type=User, site_admin=false})
//    1) Repo(id=18221276, ...
//    ...
}
