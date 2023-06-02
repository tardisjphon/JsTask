package js.task.domain.usecase

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow


interface IGetLocalDataUseCase
{
    fun invoke() : Flow<List<DomainModel>>
}