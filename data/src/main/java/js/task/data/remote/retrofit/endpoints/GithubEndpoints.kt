package js.task.data.remote.retrofit.endpoints

import js.task.data.local.db.model.GithubPage
import js.task.data.remote.retrofit.data.Github
import retrofit2.http.GET


interface GithubEndpoints
{
    @GET(Github.address)
    suspend fun getGithubPage() : List<GithubPage>
}