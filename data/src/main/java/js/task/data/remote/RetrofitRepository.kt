package js.task.data.remote

import js.task.data.local.db.model.DataModel
import js.task.data.remote.retrofit.ServiceBuilder
import js.task.data.remote.retrofit.converter.RemoteDataConverter
import js.task.data.remote.retrofit.data.DailyMotion
import js.task.data.remote.retrofit.data.Github
import js.task.data.remote.retrofit.endpoints.DailyMotionEndpoints
import js.task.data.remote.retrofit.endpoints.GithubEndpoints
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject


/**
 * TODO - zastąpić Ktor'em
 * https://medium.com/backyard-programmers/replacing-retrofit-with-ktor-client-and-kotlin-serialization-for-android-5ca6bfc60648
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

    fun get() : Flow<List<DataModel>>
    {
        val dailyFlow = flow { emit(requestDailyMotion.getDailyMotionPage()) }
        val githubFlow = flow { emit(requestGithub.getGithubPage()) }

        return combineIt(dailyFlow.map { dataConverter.getDataModel(it) },
                         githubFlow.map { dataConverter.getDataModel(it) })
    }

    private fun combineIt(
        flow1 : Flow<List<DataModel>>, flow2 : Flow<List<DataModel>>
    ) : Flow<List<DataModel>>
    {
        return combine(
                flow1,
                flow2
        ) { data1, data2 ->
            return@combine data1 + data2
        }
    }

    @OptIn(FlowPreview::class)
    private fun flattenMerge(
        flow1 : Flow<List<DataModel>>, flow2 : Flow<List<DataModel>>
    ) : Flow<List<DataModel>>
    {
        return flowOf(
                flow1,
                flow2
        ).flattenMerge()
    }
}