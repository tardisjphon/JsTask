package js.task.data.remote.retrofit.endpoints

import io.reactivex.Observable
import js.task.data.local.db.model.GithubPage
import js.task.data.remote.retrofit.data.Github
import retrofit2.http.GET


interface GithubEndpoints
{
    @GET(Github.address)
    fun getGithubPage() : Observable<List<GithubPage>>
}