package js.task.data.remote.retrofit.endpoints

import js.task.data.local.db.model.GithubPage
import js.task.data.remote.retrofit.data.Github
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface GithubEndpoints
{
    @GET(Github.address)
    fun getGithubPage() : Flow<List<GithubPage>>
}