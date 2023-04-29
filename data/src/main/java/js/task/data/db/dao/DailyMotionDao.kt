package js.task.data.db.dao

import androidx.room.*
import js.task.data.db.model.DailyMotionPage
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyMotionDao
{
    companion object
    {
        private const val selectQuery = "SELECT * FROM daily_motion_page ORDER BY id ASC"
    }

    @Query(selectQuery)
    fun get(): Flow<List<DailyMotionPage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(data: DailyMotionPage)

    @Query("DELETE FROM daily_motion_page")
    fun deleteAll()
}