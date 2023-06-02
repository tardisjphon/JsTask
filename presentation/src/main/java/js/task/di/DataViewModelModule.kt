package js.task.di

import dagger.Module
import dagger.Provides
import js.task.data.local.DbRepository
import js.task.data.remote.RetrofitRepository
import js.task.di.adapters.PresentationDataProvider
import js.task.domain.GetLocalDataUseCase
import js.task.domain.GetRemoteDataUseCase
import js.task.domain.usecase.IGetLocalDataUseCase
import js.task.domain.usecase.IGetRemoteDataUseCase
import js.task.data.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Named


@Module
class DataViewModelModule(
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
)
{

    @Provides
    fun getLocalDataUseCase(
        @Named("ValuePresentationDataProvider") presentationDataProvider : PresentationDataProvider
    ) : IGetLocalDataUseCase
    {
        return GetLocalDataUseCase(
                presentationDataProvider
        )
    }

    @Provides
    fun getRemoteDataUseCase(
        @Named("ValuePresentationDataProvider") presentationDataProvider : PresentationDataProvider,
    ) : IGetRemoteDataUseCase
    {
        return GetRemoteDataUseCase(presentationDataProvider)
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