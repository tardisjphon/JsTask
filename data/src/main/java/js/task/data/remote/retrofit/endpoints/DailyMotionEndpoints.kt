package js.task.data.remote.retrofit.endpoints

import js.task.data.local.db.model.DailyMotionPage
import js.task.data.remote.retrofit.data.DailyMotion
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface DailyMotionEndpoints
{
    @GET(DailyMotion.baseAddress)
    fun getDailyMotionPage() : Flow<DailyMotionPage>
}