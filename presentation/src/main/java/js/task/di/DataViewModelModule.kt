package js.task.di

import android.content.Context
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
import javax.inject.Named


@Module
class DataViewModelModule(private val context : Context,
                          private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{
    @Provides
    fun getDataProvider(@Named("ValueDbRepository") dbRepository: DbRepository,
                        @Named("ValueRetrofitRepository") retrofitRepository: RetrofitRepository
    ) : DataProvider
    {
        return DataProvider(
            context,
            coroutineScope,
            dbRepository,
            retrofitRepository
        )
    }

    @Provides
    fun getDataUseCase(@Named("ValueNetworkStatus") networkStatus: NetworkStatus) : DataUseCase
    {
        return GetDataUseCase(networkStatus, coroutineScope)
    }
}