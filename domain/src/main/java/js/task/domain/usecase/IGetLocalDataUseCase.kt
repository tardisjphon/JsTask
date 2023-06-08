package js.task.domain.usecase

import io.reactivex.Observable
import js.task.domain.usecase.model.DomainModel


interface IGetLocalDataUseCase
{
    fun invokeLocal() : Observable<List<DomainModel>>
}