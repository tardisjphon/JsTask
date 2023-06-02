package js.task.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import js.task.data.local.DbRepository
import js.task.data.remote.RetrofitRepository
import js.task.data.remote.retrofit.utils.NetworkStatus
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
    @Named("ValueDbRepository")
    fun provideDbRepository() : DbRepository
    {
        return DbRepository(context, coroutineScope)
    }

    @Provides
    @Named("ValueRetrofitRepository")
    fun provideRetrofitRepository() : RetrofitRepository
    {
        return RetrofitRepository()
    }

    @Provides
    @Named("ValueNetworkStatus")
    fun provideNetworkStatus() : NetworkStatus
    {
        return NetworkStatus(context)
    }
}