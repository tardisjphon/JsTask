package js.task.di

import dagger.Component
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.OnNewDataUseCase
import js.task.provider.DataProvider
import js.task.viewmodel.DataViewModel

@Component(modules = [

    DataProviderModule::class,
    GetDataUseCaseModule::class,
    OnNewDataUseCaseModule::class,
    DataViewModelModule::class
])
interface ApplicationGraph
{
    fun dataProvider() : DataProvider

    fun dataViewModel() : DataViewModel

    fun getDataUseCase() : GetDataUseCase

    fun onNewDataUseCase() : OnNewDataUseCase
}