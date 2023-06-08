package js.task.data.local.db.dao

import androidx.room.*
import io.reactivex.Observable
import js.task.data.local.db.model.DataModel


@Dao
interface DataDao
{
    companion object
    {
        private const val selectQuery = "SELECT * FROM data ORDER BY id ASC"
    }

    @Query(selectQuery)
    fun get() : Observable<List<DataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(data : List<DataModel>)

    @Query("DELETE FROM data")
    fun deleteAll()
}