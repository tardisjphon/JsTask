package js.task.di

import dagger.Component
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.OnNewDataUseCase
import js.task.provider.DataProvider
import js.task.provider.di.DataProviderModule
import js.task.viewmodel.DataViewModel

@Component(modules = [

    DataProviderModule::class,
    DataViewModelModule::class,
    GetDataUseCaseModule::class,

])
interface ApplicationGraph
{
    fun dataProvider() : DataProvider

    fun dataViewModel() : DataViewModel

    fun getDataUseCase() : GetDataUseCase

    fun onNewDataUseCase() : OnNewDataUseCase
}