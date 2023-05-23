package js.task.data.local

import android.content.Context
import js.task.data.Repository
import js.task.data.local.db.AppDatabase
import js.task.data.local.db.model.DataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class DbRepository @Inject constructor(
    private val applicationContext : Context, private val coroutinesScope : CoroutineScope
) : Repository
{
    private val db by lazy { AppDatabase.getInstance(applicationContext) }

    override fun onFailure(message : String)
    {
        Timber.w(message)
    }

    override fun getData() : Flow<List<DataModel>>
    {
        return db.dataDao().get()
    }

    override fun setData(data : List<DataModel>)
    {
        coroutinesScope.launch {
            db.runInTransaction {

                db.dataDao().apply {

                    Timber.i("setDailyMotion: deleteAll")
                    deleteAll()
                    Timber.i("setDailyMotion: set")
                    set(data)
                }
            }
        }
    }

    override fun updateData(data : List<DataModel>)
    {
        coroutinesScope.launch {
            db.runInTransaction {
                db.dataDao().apply {

                    Timber.i("setDailyMotion: set")
                    set(data)
                }
            }
        }
    }

    override suspend fun isData() : Boolean
    {
        return getData().firstOrNull()?.isNotEmpty() == true
    }
}