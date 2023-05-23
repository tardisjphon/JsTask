package js.task.domain.model

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow


interface DomainDataProvider
{
    fun download()
    fun getData() : Flow<List<DomainModel>>
    suspend fun isRepositoryData() : Boolean
}