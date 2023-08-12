package js.task.data.remote.retrofit.endpoints;

import io.reactivex.rxjava3.core.Observable;
import js.task.data.local.db.model.DailyMotionPage;
import js.task.data.remote.retrofit.data.DailyMotion;
import retrofit2.http.GET;


public interface DailyMotionEndpoints {

    @GET(DailyMotion.baseAddress)
    Observable<DailyMotionPage> getDailyMotionPage();
}