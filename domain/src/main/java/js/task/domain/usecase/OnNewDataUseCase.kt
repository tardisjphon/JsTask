package js.task.domain.usecase

import js.task.data.db.model.DataModel
import js.task.domain.model.DataResponse
import kotlinx.coroutines.flow.Flow

interface OnNewDataUseCase
{
    suspend fun invokeOnNewData(dataList: ArrayList<DataModel>) : Flow<DataResponse>
}