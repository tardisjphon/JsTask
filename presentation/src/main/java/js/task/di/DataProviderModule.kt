package js.task.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import js.task.data.DbRepository
import js.task.data.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


@Module
class DataProviderModule(@NonNull val context : Context,
                         private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{
    @Provides
    fun provideDbRepository() : DbRepository
    {
        return DbRepository(context, coroutineScope)
    }

    @Provides
    fun provideRetrofitRepository() : RetrofitRepository
    {
        return RetrofitRepository(provideDbRepository())
    }
}