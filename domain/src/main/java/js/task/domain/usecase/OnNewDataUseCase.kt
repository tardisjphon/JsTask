package js.task.domain.usecase

import js.task.domain.usecase.model.DataResponse
import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow

interface OnNewDataUseCase
{
    suspend fun invokeOnNewData(dataList : List<DomainModel>) : Flow<DataResponse>
}