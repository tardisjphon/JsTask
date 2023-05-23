package js.task.di

import dagger.Module
import dagger.Provides
import js.task.data.remote.retrofit.utils.NetworkStatus
import js.task.di.conversion.PresentationDataProvider
import js.task.di.conversion.PresentationNetworkProvider
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

    @Provides
    @Named("ValuePresentationNetworkProvider")
    fun getPresentationNetworkProvider(@Named("ValueNetworkStatus") networkStatus : NetworkStatus) : PresentationNetworkProvider
    {
        return PresentationNetworkProvider(networkStatus)
    }
}