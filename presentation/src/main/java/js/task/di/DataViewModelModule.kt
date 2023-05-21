package js.task.di

import android.content.Context
import dagger.Module
import dagger.Provides
import js.task.data.DbRepository
import js.task.data.RetrofitRepository
import js.task.data.net.utils.NetworkStatus
import js.task.domain.GetDataUseCaseImpl
import js.task.domain.OnNewDataUseCaseImpl
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.OnNewDataUseCase
import js.task.provider.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
class DataViewModelModule(
    private val context : Context,
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{
    @Provides
    @Named("ValueDataProvider")
    fun provideDataProvider(
        @Named("ValueDbRepository") dbRepository : DbRepository,
        @Named("ValueRetrofitRepository") retrofitRepository : RetrofitRepository
    ) : DataProvider
    {
        return DataProvider(
                context, coroutineScope, dbRepository, retrofitRepository
        )
    }

    @Provides
    fun getDataUseCase(
        @Named("ValueDataProvider") dataProvider : DataProvider,
        @Named("ValueNetworkStatus") networkStatus : NetworkStatus
    ) : GetDataUseCase
    {
        return GetDataUseCaseImpl(dataProvider, networkStatus, coroutineScope)
    }

    @Provides
    fun onNewDataUseCase(
        @Named("ValueDataProvider") dataProvider : DataProvider,
    ) : OnNewDataUseCase
    {
        return OnNewDataUseCaseImpl(dataProvider, coroutineScope)
    }
}