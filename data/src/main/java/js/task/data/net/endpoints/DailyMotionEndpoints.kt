package js.task.data.net.endpoints

import js.task.data.db.model.DailyMotionPage
import js.task.data.net.data.DailyMotion
import retrofit2.Call
import retrofit2.http.GET


interface DailyMotionEndpoints
{
    @GET(DailyMotion.baseAddress)
    fun getDailyMotionPage(): Call<DailyMotionPage>
}