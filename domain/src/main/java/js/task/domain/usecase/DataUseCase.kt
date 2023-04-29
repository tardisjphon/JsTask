package js.task.domain.usecase

import js.task.data.db.model.DataModel
import js.task.domain.model.DataResponse
import js.task.domain.model.GetDataResponse

interface DataUseCase
{
    fun getData(dataList: ArrayList<DataModel>, onResponse: (GetDataResponse) -> Unit)
    fun onNewData(dataList: ArrayList<DataModel>, onResponse: (DataResponse) -> Unit)
}