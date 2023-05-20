package js.task.data.db.dao

import androidx.room.*
import js.task.data.db.model.DataModel
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao
{
    companion object
    {
        private const val selectQuery = "SELECT * FROM data ORDER BY id ASC"
    }

    @Query(selectQuery)
    fun get(): Flow<List<DataModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(data: List<DataModel>)

    @Query("DELETE FROM data")
    fun deleteAll()
}