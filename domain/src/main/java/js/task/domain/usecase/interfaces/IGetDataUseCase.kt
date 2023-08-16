package js.task.domain.usecase.interfaces

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow


interface IGetDataUseCase
{
    fun invoke(data: Flow<List<DomainModel>>) {}
}