package js.task.di

import dagger.Module
import dagger.Provides
import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.interfaces.IGetDataUseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named


@Module
class DataViewModelModule
{
    @Provides
    fun getDataUseCase(
        @Named("coroutineScope")
        coroutineScope : CoroutineScope,
        @Named("iDataProvider")
        iDataProvider : IDataProvider
    ) : IGetDataUseCase
    {
        return GetDataUseCase(
                coroutineScope,
                iDataProvider
        )
    }
}