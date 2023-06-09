package js.task.data.local

import js.task.data.Repository
import js.task.data.local.db.AppDatabase
import js.task.data.local.db.model.DataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class DbRepository @Inject constructor(
    private val db : AppDatabase,
    private val coroutinesScope : CoroutineScope
) : Repository
{
    override fun getData() : Flow<List<DataModel>>
    {
        return db.dataDao()
            .get()
    }

    override fun setData(data : List<DataModel>)
    {
        coroutinesScope.launch {
            db.runInTransaction {
                db.dataDao()
                    .apply {

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
                db.dataDao()
                    .apply {

                        Timber.i("setDailyMotion: set")
                        set(data)
                    }
            }
        }
    }
}