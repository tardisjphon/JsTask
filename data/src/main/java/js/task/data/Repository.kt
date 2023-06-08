package js.task.data

import io.reactivex.Observable
import js.task.data.local.db.model.DataModel


interface Repository
{
    fun getData() : Observable<List<DataModel>>
    fun updateData(data : List<DataModel>)
    fun setData(data : List<DataModel>)
}