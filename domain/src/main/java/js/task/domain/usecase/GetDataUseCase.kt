package js.task.domain.usecase

import js.task.domain.usecase.model.DomainModel
import js.task.domain.usecase.model.GetDataResponse

interface GetDataUseCase
{
    suspend fun invokeGetData(dataList : List<DomainModel>) : GetDataResponse
}