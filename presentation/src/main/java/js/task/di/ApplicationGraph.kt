package js.task.di

import dagger.Component
import js.task.data.di.DataProviderModule
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.viewmodel.DataViewModel


@Component(
        modules = [DataProviderModule::class, DataViewModelModule::class]
)
interface ApplicationGraph
{
    fun dataViewModel() : DataViewModel

    fun dataUseCase() : IGetDataUseCase
}