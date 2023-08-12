package js.task.data.local;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import js.task.data.Repository;
import js.task.data.local.db.AppDatabase;
import js.task.data.local.db.model.DataModel;
import timber.log.Timber;


public class DbRepository
        implements Repository
{
    private final AppDatabase db;
    private final CompositeDisposable compositeDisposable;

    public DbRepository(
            AppDatabase dbIn,
            CompositeDisposable compositeDisposableIn
    )
    {
        db = dbIn;
        compositeDisposable = compositeDisposableIn;
    }

    @Override
    public Observable<List<DataModel>> getData()
    {
        return db.dataDao()
                .get();
    }

    @Override
    public void updateData(List<DataModel> data)
    {
        Disposable disposable = Observable.empty()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> db.runInTransaction(() ->
                {
                    Timber.i("DataModel: set");
                    db.dataDao()
                            .set(data);
                }))
                .subscribe();
        compositeDisposable.addAll(disposable);
    }

    @Override
    public void setData(List<DataModel> data)
    {
        Disposable disposable = Observable.empty()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> db.runInTransaction(() ->
                {
                    Timber.i("DataModel: deleteAll");
                    db.dataDao()
                            .deleteAll();
                    Timber.i("DataModel: set");
                    db.dataDao()
                            .set(data);
                }))
                .subscribe();
        compositeDisposable.addAll(disposable);
    }

}
