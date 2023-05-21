package js.task.di

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


@Module
class OnNewDataUseCaseModule(private val context : Context,
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
}