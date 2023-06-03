package js.task.di

import dagger.Module
import dagger.Provides
import js.task.di.adapters.PresentationDataProvider
import js.task.data.DataProvider
import javax.inject.Named


@Module
class GetDataUseCaseModule
{
    @Provides
    fun provideDataProvider(@Named("ValueDataProvider") dataProvider : DataProvider) : DataProvider
    {
        return dataProvider
    }

    @Provides
    @Named("ValuePresentationDataProvider")
    fun getPresentationDataProvider(@Named("ValueDataProvider") dataProvider : DataProvider) : PresentationDataProvider
    {
        return PresentationDataProvider(dataProvider)
    }
}