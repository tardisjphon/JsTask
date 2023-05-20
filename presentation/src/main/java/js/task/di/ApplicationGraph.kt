package js.task.di

import dagger.Component
import js.task.domain.GetDataUseCase
import js.task.provider.DataProvider
import js.task.viewmodel.DataViewModel


@Component(modules = [

    DataProviderModule::class,
    GetDataUseCaseModule::class,
    DataViewModelModule::class
])
interface ApplicationGraph
{
    fun dataProvider() : DataProvider

    fun dataViewModel() : DataViewModel

    fun getDataUseCase() : GetDataUseCase
}