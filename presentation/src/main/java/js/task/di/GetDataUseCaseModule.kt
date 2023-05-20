package js.task.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import js.task.data.net.utils.NetworkStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


@Module
class GetDataUseCaseModule(@NonNull private val context : Context,
                           private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO))
{
    @Provides
    fun provideContext() : Context {
        return context
    }

    @Provides
    fun provideNetworkStatus() : NetworkStatus {
        return NetworkStatus(context)
    }

    @Provides
    fun provideCoroutineScope() : CoroutineScope {
        return coroutineScope
    }
}