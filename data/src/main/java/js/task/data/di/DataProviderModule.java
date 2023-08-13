package js.task.data.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import js.task.data.DataProvider;
import js.task.data.local.DbRepository;
import js.task.data.local.db.AppDatabase;
import js.task.data.remote.RetrofitRepository;
import js.task.domain.interfaces.IDataProvider;
import javax.inject.Named;


@Module
public class DataProviderModule
{
    private final Context context;

    public DataProviderModule(Context context)
    {
        this.context = context;
    }

    @Provides
    @Named("compositeDisposable")
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Named("iDataProvider")
    public IDataProvider provideDataProvider()
    {
        return new DataProvider(
                provideCompositeDisposable(),
                provideDbRepository(),
                provideRetrofitRepository()
        );
    }

    @Provides
    @Named("dbRepository")
    public DbRepository provideDbRepository()
    {
        return new DbRepository(
                getDb(),
                provideCompositeDisposable()
        );
    }

    @Provides
    @Named("retrofitRepository")
    public RetrofitRepository provideRetrofitRepository()
    {
        return new RetrofitRepository();
    }

    @Provides
    public AppDatabase getDb()
    {
        return new AppDatabase.AppDatabaseCompanion().getInstance(this.context);
    }
}