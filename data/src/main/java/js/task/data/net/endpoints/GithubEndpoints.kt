package js.task.data.net.endpoints

import js.task.data.db.model.GithubPage
import js.task.data.net.data.Github
import retrofit2.http.GET


interface GithubEndpoints
{
    @GET(Github.address)
    suspend fun getGithubPage(): List<GithubPage>
}