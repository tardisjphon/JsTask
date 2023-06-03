package js.task.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import js.task.data.local.DbRepository
import js.task.data.local.db.AppDatabase
import js.task.data.remote.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
class DataProviderModule(
    private val context : Context,
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{
    private fun getDb() : AppDatabase
    {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Named("ValueDbRepository")
    fun provideDbRepository() : DbRepository
    {
        return DbRepository(
                getDb(),
                coroutineScope
        )
    }

    @Provides
    @Named("ValueRetrofitRepository")
    fun provideRetrofitRepository() : RetrofitRepository
    {
        return RetrofitRepository()
    }
}