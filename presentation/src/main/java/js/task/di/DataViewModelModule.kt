package js.task.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import js.task.data.DbRepository
import js.task.data.RetrofitRepository
import js.task.data.net.utils.NetworkStatus
import js.task.domain.GetDataUseCase
import js.task.domain.usecase.DataUseCase
import js.task.provider.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


@Module
class DataViewModelModule(@NonNull private val context : Context,
                          private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{
    @Provides
    fun getDataProvider() : DataProvider
    {
        val dbRepository = DbRepository(context, coroutineScope)
        return DataProvider(
            context,
            coroutineScope,
            dbRepository,
            RetrofitRepository(dbRepository)
        ) //TODO   DbRepository + RetrofitRepository ma też DataProviderModule
    }

    @Provides
    fun getDataUseCase() : DataUseCase
    {
        return GetDataUseCase(NetworkStatus(context), coroutineScope)
    }
}