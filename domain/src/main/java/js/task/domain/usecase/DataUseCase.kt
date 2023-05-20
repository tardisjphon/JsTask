package js.task.domain.usecase

import js.task.data.db.model.DataModel
import js.task.domain.model.DataResponse
import js.task.domain.model.GetDataResponse
import js.task.provider.DataProvider

interface DataUseCase
{
    fun getData(dataProvider: DataProvider,
                dataList: ArrayList<DataModel>,
                onResponse: (GetDataResponse) -> Unit)
    fun onNewData(dataProvider: DataProvider,
                  dataList: ArrayList<DataModel>,
                  onResponse: (DataResponse) -> Unit)
}