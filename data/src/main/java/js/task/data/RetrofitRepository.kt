package js.task.data

import js.task.data.local.model.DataModel
import js.task.data.remote.ServiceBuilder
import js.task.data.remote.ServiceBuilder2
import js.task.data.remote.data.DailyMotion
import js.task.data.remote.data.Github
import js.task.data.remote.endpoints.DailyMotionEndpoints
import js.task.data.remote.endpoints.GithubEndpoints
import js.task.data.remote.converter.DataConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class RetrofitRepository @Inject constructor(
    private val delegateObject : IRepository
) : IRepository by delegateObject
{
    private val dataConverter by lazy { DataConverter() }

    private val requestDailyMotion by lazy {
        ServiceBuilder.buildService(
                DailyMotion.baseAddress, DailyMotionEndpoints::class.java
        )
    }

    private val requestGithub by lazy {
        ServiceBuilder2.buildService(
                Github.baseAddress, GithubEndpoints::class.java
        )
    }

    fun get()
    {
        val dailyFlow = flow { emit(requestDailyMotion.getDailyMotionPage()) }
        val githubFlow = flow { emit(requestGithub.getGithubPage()) }
        val dailyFlowMapped = dailyFlow.map { dataConverter.dailyMotionToDataModelList(it) }
        val githubFlowMapped = githubFlow.map { dataConverter.gitHubToDataModelList(it) }

        combineIt(dailyFlowMapped, githubFlowMapped)
    }

    private fun combineIt(
        flow1 : Flow<LinkedList<DataModel>>, flow2 : Flow<LinkedList<DataModel>>
    )
    {
        combine(flow1, flow2) { data1, data2 ->
            val list = LinkedList<DataModel>()
            list.addAll(data1)
            list.addAll(data2)
            delegateObject.updateData(list)
        }.catch { delegateObject.onFailure(it.message.toString()) }
            .launchIn(CoroutineScope(Dispatchers.IO))
    }

    @OptIn(FlowPreview::class)
    private fun flattenMerge(
        flow1 : Flow<LinkedList<DataModel>>, flow2 : Flow<LinkedList<DataModel>>
    )
    {
        CoroutineScope(Dispatchers.IO).launch {
            flowOf(flow1, flow2).flattenMerge()
                .catch { delegateObject.onFailure(it.message.toString()) }
                .collect { delegateObject.updateData(it) }
        }
    }
}