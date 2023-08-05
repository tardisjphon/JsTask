package js.task.domain.usecase.interfaces

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.MutableSharedFlow


interface IGetDataUseCase
{
    fun invoke(data: MutableSharedFlow<List<DomainModel>>) {}
}