package js.task.di.conversion

import js.task.domain.model.DomainDataProvider
import js.task.domain.usecase.model.DomainModel
import js.task.provider.DataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PresentationDataProvider @Inject constructor(private val dataProvider : DataProvider) : DomainDataProvider
{
    override fun download()
    {
        dataProvider.download()
    }

    override fun getData() : Flow<List<DomainModel>>
    {
        return dataProvider.getData().map {
            it.map {
                DomainModel(
                        it.id ?: -1,
                        it.userName ?: "",
                        it.imageUrl ?: "",
                        it.apiName?.name ?: ""
                )
            }
        }
    }

    override suspend fun isRepositoryData() : Boolean
    {
        return dataProvider.isRepositoryData()
    }
}