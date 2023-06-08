package js.task.data.di;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import js.task.data.local.DbRepository;
import js.task.data.local.db.AppDatabase;
import js.task.data.remote.RetrofitRepository;


@Module
public class DataProviderModule
{
    private final Context context;
    private final CompositeDisposable compositeDisposable;

    public DataProviderModule(Context contextIn, CompositeDisposable compositeDisposableIn)
    {
        context = contextIn;
        compositeDisposable = compositeDisposableIn;
    }

    private AppDatabase getDb()
    {
        return AppDatabase.Companion.getInstance(context);
    }

    @Provides
    @Named("ValueDbRepository")
    public DbRepository provideDbRepository()
    {
        return new DbRepository(getDb(), compositeDisposable);
    }

    @Provides
    @Named("ValueRetrofitRepository")
    public RetrofitRepository provideRetrofitRepository()
    {
        return new RetrofitRepository();
    }
}
