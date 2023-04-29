package js.task.data

import js.task.data.db.model.DailyMotionPage
import js.task.data.db.model.DataModel
import js.task.data.db.model.GithubPage
import js.task.data.net.ServiceBuilder
import js.task.data.net.ServiceBuilder2
import js.task.data.net.data.DailyMotion
import js.task.data.net.data.Github
import js.task.data.net.endpoints.DailyMotionEndpoints
import js.task.data.net.endpoints.GithubEndpoints
import js.task.provider.converter.DataConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


class RetrofitRepository @Inject constructor(
    private val delegateObject: IRepository) : IRepository by delegateObject
{
    private val dataConverter by lazy { DataConverter() }

    private val requestDailyMotion by lazy {
        ServiceBuilder.buildService(
            DailyMotion.baseAddress,
            DailyMotionEndpoints::class.java
        )
    }
    private var dailyMotionCall: Call<DailyMotionPage> ?= null

    private val requestGithub by lazy {
        ServiceBuilder2.buildService(
            Github.baseAddress,
            GithubEndpoints::class.java
        )
    }
    private var githubCall: Call<List<GithubPage>> ?= null

    private var flowDM: Flow<LinkedList<DataModel>> ?= null
    private var flowGH: Flow<LinkedList<DataModel>> ?= null


    fun getDm()
    {
        dailyMotionCall = requestDailyMotion.getDailyMotionPage()

        dailyMotionCall?.enqueue(object : Callback<DailyMotionPage>
        {
            override fun onResponse(
                call: Call<DailyMotionPage>,
                response: Response<DailyMotionPage>
            ) {
                if (response.isSuccessful) {
                    val dailyMotionPage = response.body() as DailyMotionPage
                    val data = dataConverter.dailyMotionToDataModelList(dailyMotionPage)

                    flowDM = listOf(data).asFlow()

                    combineFlows(flowDM, flowGH)
                }
                else delegateObject.onFailure(response.errorBody().toString())
            }

            override fun onFailure(call: Call<DailyMotionPage>, t: Throwable) {
                delegateObject.onFailure(t.message ?: "")
            }
        })
    }

    fun getGh()
    {
        githubCall = requestGithub.getGithubPage()

        githubCall?.enqueue(object : Callback<List<GithubPage>>
        {
            override fun onResponse(
                call: Call<List<GithubPage>>,
                response: Response<List<GithubPage>>
            ) {
                if (response.isSuccessful) {
                    val githubPage = response.body() as List<GithubPage>
                    val data = dataConverter.gitHubToDataModelList(githubPage)

                    flowGH = listOf(data).asFlow()

                    combineFlows(flowDM, flowGH)
                }
                else delegateObject.onFailure(response.errorBody().toString())
            }

            override fun onFailure(call: Call<List<GithubPage>>, t: Throwable) {
                delegateObject.onFailure(t.message ?: "")
            }
        })
    }

    private fun combineFlows(flowDM: Flow<LinkedList<DataModel>>?, flowGH: Flow<LinkedList<DataModel>>?)
    {
        if (flowDM == null || flowGH == null) return

        combine(flowDM, flowGH) { dm, gh ->

            val dataList = LinkedList<DataModel>()
            dataList.addAll(dm)
            dataList.addAll(gh)

            delegateObject.setDataModel(dataList)

        }.launchIn(CoroutineScope(Dispatchers.IO))
    }
}