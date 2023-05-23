package js.task.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import js.task.data.local.db.converter.DailyMotionConverter
import js.task.data.local.db.dao.DataDao
import js.task.data.local.db.model.DataModel

@Database(entities = [DataModel::class], version = 1, exportSchema = false)
@TypeConverters(DailyMotionConverter::class)
abstract class AppDatabase : RoomDatabase()
{
    companion object
    {
        private const val NAME = "db_js_task"

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }


        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, NAME).build()
    }

    abstract fun dataDao() : DataDao
}