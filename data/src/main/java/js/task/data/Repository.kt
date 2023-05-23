package js.task.data

import js.task.data.local.db.model.DataModel
import kotlinx.coroutines.flow.Flow


interface Repository
{
    fun onFailure(message : String)
    fun getData() : Flow<List<DataModel>>
    suspend fun isData() : Boolean
    fun updateData(data : List<DataModel>)
    fun setData(data : List<DataModel>)
}