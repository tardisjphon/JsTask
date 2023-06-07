package js.task.di

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import js.task.data.DataProvider
import js.task.data.local.DbRepository
import js.task.data.remote.RetrofitRepository
import js.task.di.adapters.PresentationDataProvider
import js.task.domain.usecase.GetLocalDataUseCase
import js.task.domain.usecase.GetRemoteDataUseCase
import js.task.domain.usecase.IGetLocalDataUseCase
import js.task.domain.usecase.IGetRemoteDataUseCase
import javax.inject.Named


@Module
class DataViewModelModule(
    private val compositeDisposable : CompositeDisposable
)
{

    @Provides
    fun getLocalDataUseCase(
        @Named("ValuePresentationDataProvider")
        presentationDataProvider : PresentationDataProvider
    ) : IGetLocalDataUseCase
    {
        return GetLocalDataUseCase(
                presentationDataProvider
        )
    }

    @Provides
    fun getRemoteDataUseCase(
        @Named("ValuePresentationDataProvider")
        presentationDataProvider : PresentationDataProvider,
    ) : IGetRemoteDataUseCase
    {
        return GetRemoteDataUseCase(presentationDataProvider)
    }


    @Provides
    @Named("ValueDataProvider")
    fun provideDataProvider(
        @Named("ValueDbRepository")
        dbRepository : DbRepository,
        @Named("ValueRetrofitRepository")
        retrofitRepository : RetrofitRepository
    ) : DataProvider
    {
        return DataProvider(
                compositeDisposable,
                dbRepository,
                retrofitRepository
        )
    }
}