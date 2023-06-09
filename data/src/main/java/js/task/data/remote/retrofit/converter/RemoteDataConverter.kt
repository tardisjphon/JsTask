package js.task.data.remote.retrofit.converter

import js.task.data.local.db.model.DailyMotionPage
import js.task.data.local.db.model.DataModel
import js.task.data.local.db.model.GithubPage
import js.task.data.remote.retrofit.converter.model.ApiName
import javax.inject.Inject


class RemoteDataConverter @Inject constructor()
{
    fun getDataModel(data : DailyMotionPage) : List<DataModel>
    {
        return data.list?.mapIndexed { index, dailyMotion ->
            DataModel(
                    id = index,
                    userName = dailyMotion?.screenName ?: "",
                    imageUrl = "",
                    apiName = ApiName.DAILY_MOTION
            )
        }
                   ?.sortedByDescending { it.apiName?.name } ?: ArrayList()
    }

    fun getDataModel(data : List<GithubPage>) : List<DataModel>
    {
        return data.map {
            DataModel(
                    id = it.id,
                    userName = it.login,
                    imageUrl = it.avatarUrl,
                    apiName = ApiName.GITHUB
            )
        }
            .sortedByDescending { it.apiName?.name }
    }
}