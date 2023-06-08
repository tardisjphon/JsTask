package js.task.data.remote.retrofit.endpoints;

import java.util.List;

import io.reactivex.Observable;
import js.task.data.local.db.model.GithubPage;
import js.task.data.remote.retrofit.data.Github;
import retrofit2.http.GET;


public interface GithubEndpoints {

    @GET(Github.address)
    Observable<List<GithubPage>> getGithubPage();
}