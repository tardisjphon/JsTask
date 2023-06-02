package js.task.di.adapters

import js.task.domain.usecase.IDomainDataProvider
import js.task.domain.usecase.model.DomainModel
import js.task.data.DataProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PresentationDataProvider @Inject constructor(private val dataProvider : DataProvider) :
    IDomainDataProvider
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
}