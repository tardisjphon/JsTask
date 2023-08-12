package js.task.data.remote;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.schedulers.Schedulers;
import js.task.data.local.db.model.DailyMotionPage;
import js.task.data.local.db.model.DataModel;
import js.task.data.local.db.model.GithubPage;
import js.task.data.remote.retrofit.ServiceBuilder;
import js.task.data.remote.retrofit.converter.RemoteDataConverter;
import js.task.data.remote.retrofit.data.DailyMotion;
import js.task.data.remote.retrofit.data.Github;
import js.task.data.remote.retrofit.endpoints.DailyMotionEndpoints;
import js.task.data.remote.retrofit.endpoints.GithubEndpoints;
import kotlin.Lazy;
import timber.log.Timber;


public class RetrofitRepository {
    private final Lazy<RemoteDataConverter> dataConverter = kotlin.LazyKt.lazy(RemoteDataConverter::new);

    private final Lazy<DailyMotionEndpoints> requestDailyMotion = kotlin.LazyKt.lazy(() ->
            new ServiceBuilder().buildService(
                    DailyMotion.baseAddress,
                    DailyMotionEndpoints.class
            )
    );

    private final Lazy<GithubEndpoints> requestGithub = kotlin.LazyKt.lazy(() ->
            new ServiceBuilder().buildService(
                    Github.baseAddress,
                    GithubEndpoints.class
            )
    );


    public Observable<List<DataModel>> get() {
        Observable<DailyMotionPage> dailyFlow = requestDailyMotion.getValue().getDailyMotionPage();
        Observable<List<GithubPage>> githubFlow = requestGithub.getValue().getGithubPage();

        return merge(
                dailyFlow.map(it ->
                        dataConverter.getValue().getDataModel(it)),
                githubFlow.map(it ->
                        dataConverter.getValue().getDataModel(it))
        );
    }

    private Observable<List<DataModel>> merge(
            Observable<List<DataModel>> observable1,
            Observable<List<DataModel>> observable2
    ) {
        return Observable.merge(
                        observable1,
                        observable2
                )
                .subscribeOn(Schedulers.io())
                .doOnError(Timber::e);
    }

    private Observable<List<DataModel>> zip(
            Observable<List<DataModel>> observable1,
            Observable<List<DataModel>> observable2
    ) {
        BiFunction<List<DataModel>, List<DataModel>, List<DataModel>> zipper = (BiFunction<List<DataModel>, List<DataModel>, List<DataModel>>) (list1, list2) ->
                Stream.concat(list1.stream(), list2.stream())
                        .collect(Collectors.toList());

        return Observable.zip(
                        observable1,
                        observable2,
                        zipper
                )
                .subscribeOn(Schedulers.io())
                .doOnError(Timber::e);
    }
}