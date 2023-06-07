package js.task.data.remote

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import js.task.data.local.db.model.DataModel
import js.task.data.remote.retrofit.ServiceBuilder
import js.task.data.remote.retrofit.converter.RemoteDataConverter
import js.task.data.remote.retrofit.data.DailyMotion
import js.task.data.remote.retrofit.data.Github
import js.task.data.remote.retrofit.endpoints.DailyMotionEndpoints
import js.task.data.remote.retrofit.endpoints.GithubEndpoints
import timber.log.Timber
import javax.inject.Inject


/**
 * TODO - zastąpić Ktor'em
 * https://medium.com/backyard-programmers/replacing-retrofit-with-ktor-client-and-kotlin-serialization-for-android-5ca6bfc60648
 *
 * https://www.digitalocean.com/community/tutorials/android-rxjava-retrofit
 */
class RetrofitRepository @Inject constructor()
{
    private val dataConverter by lazy { RemoteDataConverter() }

    private val requestDailyMotion by lazy {
        ServiceBuilder().buildService(
                DailyMotion.baseAddress,
                DailyMotionEndpoints::class.java
        )
    }

    private val requestGithub by lazy {
        ServiceBuilder().buildService(
                Github.baseAddress,
                GithubEndpoints::class.java
        )
    }

    fun get() : Observable<List<DataModel>>
    {
        val dailyFlow = requestDailyMotion.getDailyMotionPage()
        val githubFlow = requestGithub.getGithubPage()

        return merge(
                dailyFlow.map { dataConverter.getDataModel(it) },
                githubFlow.map { dataConverter.getDataModel(it) }
        )
    }

    private fun merge(
        observable1 : Observable<List<DataModel>>, observable2 : Observable<List<DataModel>>
    ) : Observable<List<DataModel>>
    {
        return Observable.merge(
                observable1,
                observable2
        )
            .subscribeOn(Schedulers.computation())
            .doOnError {
                Timber.e("exception: ${it.message}")
            }
    }

    private fun zip(
        observable1 : Observable<List<DataModel>>, observable2 : Observable<List<DataModel>>
    ) : Observable<List<DataModel>>
    {
        return Observable.zip(
                observable1,
                observable2
        ) { str1, str2 -> str1 + str2 }
            .subscribeOn(Schedulers.computation())
            .doOnError {
                Timber.e("exception: ${it.message}")
            }
    }
}