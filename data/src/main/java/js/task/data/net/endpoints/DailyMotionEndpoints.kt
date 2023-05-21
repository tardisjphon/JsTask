package js.task.data.net.endpoints

import js.task.data.db.model.DailyMotionPage
import js.task.data.net.data.DailyMotion
import retrofit2.http.GET

interface DailyMotionEndpoints
{
    @GET(DailyMotion.baseAddress)
    suspend fun getDailyMotionPage() : DailyMotionPage
}