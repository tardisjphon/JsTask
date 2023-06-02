package js.task.domain.usecase

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow


interface IDomainDataProvider
{
    fun download()
    fun getData() : Flow<List<DomainModel>>
}