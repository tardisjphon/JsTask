package js.task.di.adapters

import io.reactivex.Observable
import js.task.domain.usecase.IDomainDataProvider
import js.task.domain.usecase.model.DomainModel
import js.task.data.DataProvider
import javax.inject.Inject


class PresentationDataProvider @Inject constructor(private val dataProvider : DataProvider) :
    IDomainDataProvider
{
    override fun download()
    {
        dataProvider.download()
    }

    override fun getData() : Observable<List<DomainModel>>
    {
        return dataProvider.data.map {
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