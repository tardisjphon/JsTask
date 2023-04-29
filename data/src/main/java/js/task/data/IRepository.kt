package js.task.data

import js.task.data.db.model.DataModel
import kotlinx.coroutines.flow.Flow


interface IRepository {
    fun onFailure(message: String)

    fun getDataModel() : Flow<List<DataModel>>
    suspend fun isData() : Boolean
    fun setDataModel(data: List<DataModel>)
}