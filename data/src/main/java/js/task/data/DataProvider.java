package js.task.data;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import js.task.data.local.DbRepository;
import js.task.data.local.db.model.DataModel;
import js.task.data.remote.RetrofitRepository;


public class DataProvider {

    private final CompositeDisposable compositeDisposable;
    private final DbRepository localRepository;
    private final RetrofitRepository remoteRepository;

    @Inject
    public DataProvider(
            CompositeDisposable compositeDisposableInject,
            DbRepository localRepositoryInject,
            RetrofitRepository remoteRepositoryInject
    ) {
        compositeDisposable = compositeDisposableInject;
        localRepository     = localRepositoryInject;
        remoteRepository    = remoteRepositoryInject;
    }

    public void download() {
        compositeDisposable.addAll(remoteRepository.get().subscribe(
                localRepository::updateData
        ));
    }

    public Observable<List<DataModel>> getData() {
        return localRepository.getData();
    }
}
