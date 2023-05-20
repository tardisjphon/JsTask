package js.task.di

import android.content.Context
import dagger.Module
import dagger.Provides
import js.task.data.net.utils.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
class GetDataUseCaseModule(private val context : Context,
                           private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO))
{
    @Provides
    fun provideContext() : Context {
        return context
    }

    @Provides
    fun provideCoroutineScope() : CoroutineScope {
        return coroutineScope
    }

    @Provides
    fun provideNetworkStatus(@Named("ValueNetworkStatus") networkStatus: NetworkStatus) : NetworkStatus {
        return networkStatus
    }
}