package js.task.di

import dagger.Component
import js.task.domain.GetDataUseCase
import js.task.provider.DataProvider


@Component(modules = [DataProviderModule::class, GetDataUseCaseModule::class])
interface ApplicationGraph
{
    fun dataProvider() : DataProvider
    fun getDataUseCase() : GetDataUseCase
}