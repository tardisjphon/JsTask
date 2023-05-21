package js.task.domain.usecase

import js.task.data.db.model.DataModel
import js.task.domain.model.GetDataResponse

interface GetDataUseCase
{
    suspend fun invokeGetData(dataList : ArrayList<DataModel>) : GetDataResponse
}