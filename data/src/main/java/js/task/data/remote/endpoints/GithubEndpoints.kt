package js.task.data.remote.endpoints

import js.task.data.local.model.GithubPage
import js.task.data.remote.data.Github
import retrofit2.http.GET

interface GithubEndpoints
{
    @GET(Github.address)
    suspend fun getGithubPage() : List<GithubPage>
}