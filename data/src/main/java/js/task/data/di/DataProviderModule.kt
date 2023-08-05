package js.task.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import js.task.data.DataProvider
import js.task.data.local.DbRepository
import js.task.data.local.db.AppDatabase
import js.task.data.remote.RetrofitRepository
import js.task.domain.interfaces.IDataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
class DataProviderModule(
    private val context : Context,
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{
    @Provides
    @Named("iDataProvider")
    fun provideDataProvider() : IDataProvider
    {
        return DataProvider(
                provideCoroutineScope(),
                provideDbRepository(),
                provideRetrofitRepository()
        )
    }

    @Provides
    @Named("dbRepository")
    fun provideDbRepository() : DbRepository
    {
        return DbRepository(
                getDb(),
                provideCoroutineScope()
        )
    }

    @Provides
    @Named("retrofitRepository")
    fun provideRetrofitRepository() : RetrofitRepository
    {
        return RetrofitRepository()
    }

    @Provides
    @Named("coroutineScope")
    fun provideCoroutineScope() : CoroutineScope
    {
        return coroutineScope
    }

    @Provides
    fun getDb() : AppDatabase
    {
        return AppDatabase.getInstance(context)
    }
}