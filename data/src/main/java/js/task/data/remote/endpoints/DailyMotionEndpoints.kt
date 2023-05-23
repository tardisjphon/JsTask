package js.task.data.remote.endpoints

import js.task.data.local.model.DailyMotionPage
import js.task.data.remote.data.DailyMotion
import retrofit2.http.GET

interface DailyMotionEndpoints
{
    @GET(DailyMotion.baseAddress)
    suspend fun getDailyMotionPage() : DailyMotionPage
}