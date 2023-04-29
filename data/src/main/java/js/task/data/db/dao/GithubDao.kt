package js.task.data.db.dao

import androidx.room.*
import js.task.data.db.model.GithubPage
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubDao
{
    companion object
    {
        private const val selectQuery = "SELECT * FROM github_page ORDER BY id ASC"
    }

    @Query(selectQuery)
    fun get(): Flow<List<GithubPage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun set(data: List<GithubPage>)

    @Query("DELETE FROM github_page")
    fun deleteAll()
}