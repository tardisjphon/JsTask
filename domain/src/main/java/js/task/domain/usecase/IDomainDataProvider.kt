package js.task.domain.usecase

import js.task.domain.usecase.model.DomainModel
import io.reactivex.Observable


interface IDomainDataProvider
{
    fun download()
    fun getData() : Observable<List<DomainModel>>
}