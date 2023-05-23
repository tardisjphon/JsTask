package js.task.di

import dagger.Module
import dagger.Provides
import js.task.data.DbRepository
import js.task.data.RetrofitRepository
import js.task.di.conversion.PresentationDataProvider
import js.task.di.conversion.PresentationNetworkProvider
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
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{

    @Provides
    fun getDataUseCase(
        @Named("ValuePresentationDataProvider") presentationDataProvider : PresentationDataProvider,
        @Named("ValuePresentationNetworkProvider") presentationNetworkProvider : PresentationNetworkProvider
    ) : GetDataUseCase
    {
        return GetDataUseCaseImpl(
                presentationDataProvider, presentationNetworkProvider, coroutineScope
        )
    }

    @Provides
    fun onNewDataUseCase(
        @Named("ValuePresentationDataProvider") presentationDataProvider : PresentationDataProvider,
    ) : OnNewDataUseCase
    {
        return OnNewDataUseCaseImpl(presentationDataProvider, coroutineScope)
    }


    @Provides
    @Named("ValueDataProvider")
    fun provideDataProvider(
        @Named("ValueDbRepository") dbRepository : DbRepository,
        @Named("ValueRetrofitRepository") retrofitRepository : RetrofitRepository
    ) : DataProvider
    {
        return DataProvider(
                coroutineScope, dbRepository, retrofitRepository
        )
    }
}