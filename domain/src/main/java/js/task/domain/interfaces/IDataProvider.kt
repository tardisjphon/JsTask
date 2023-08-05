package js.task.domain.interfaces

import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow


interface IDataProvider
{
    fun download()
    fun getData() : Flow<List<DomainModel>>
}