package js.task.di

import dagger.Component
import js.task.domain.usecase.IGetLocalDataUseCase
import js.task.domain.usecase.IGetRemoteDataUseCase
import js.task.data.DataProvider
import js.task.data.di.DataProviderModule
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

    fun getLocalDataUseCase() : IGetLocalDataUseCase

    fun getRemoteDataUseCase() : IGetRemoteDataUseCase
}