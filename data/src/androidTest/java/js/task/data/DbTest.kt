package js.task.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import js.task.data.local.db.AppDatabase
import js.task.data.local.db.dao.DataDao
import js.task.data.local.db.model.DataModel
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class DbTest
{
    private lateinit var database : AppDatabase
    private lateinit var dataDao : DataDao

    @Before
    fun setupDatabase()
    {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
        )
            .build()

        dataDao = database.dataDao()
    }

    @Test
    fun insertData_returnsTrue() =
        runBlocking {
            val dataModel = DataModel(
                    1,
                    userName = "Batman"
            )
            dataDao.set(listOf(dataModel))

            dataDao.get()
                .test {
                    val list = awaitItem()
                    val result = list.contains(dataModel)
                    println("insertData_returnsTrue, result: $result")
                    assert(result)
                }
        }
}