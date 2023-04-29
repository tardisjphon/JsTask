package js.task.provider.converter

import js.task.data.db.model.DailyMotionPage
import js.task.data.db.model.GithubPage
import js.task.provider.model.ApiName
import js.task.data.db.model.DataModel
import java.util.*
import javax.inject.Inject


class DataConverter @Inject constructor()
{
    fun dailyMotionToDataModelList(dailyMotionPage: DailyMotionPage) : LinkedList<DataModel>
    {
        val dataList = LinkedList<DataModel>()
        var i = 0
        dailyMotionPage.list?.forEach {
            it?.let {
                dataList.add(
                    DataModel(
                    id = i++,
                    userName = it.screenname ?: "",
                    imageUrl = "",
                    apiName = ApiName.DAILY_MOTION)
                )
            }
        }
        return dataList
    }

    fun gitHubToDataModelList(githubPage: List<GithubPage>) : LinkedList<DataModel>
    {
        val dataList = LinkedList<DataModel>()
        githubPage.forEach {
            dataList.add(
                DataModel(
                id = it.id,
                userName = it.login,
                imageUrl = it.avatar_url,
                    apiName = ApiName.GITHUB)
            )
        }
        return dataList
    }

    private fun sort(dataList: LinkedList<DataModel>)
    {
        val sortedList = dataList.sortedByDescending { it.apiName?.name }
        dataList.clear()
        dataList.addAll(sortedList)
    }
}