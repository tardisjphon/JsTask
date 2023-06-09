package js.task.data.local.db.dao

import androidx.room.*
import js.task.data.local.db.model.DataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao
{
    @Query("SELECT * FROM data ORDER BY id ASC")
    fun get() : Flow<List<DataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(data : List<DataModel>)

    @Query("DELETE FROM data")
    fun deleteAll()
}