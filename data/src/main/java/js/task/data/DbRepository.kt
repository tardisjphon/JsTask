package js.task.data

import android.content.Context
import js.task.data.db.AppDatabase
import js.task.data.db.model.DataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class DbRepository @Inject constructor(
    private val applicationContext: Context,
    private val coroutinesScope: CoroutineScope
) : IRepository
{
    private val db by lazy { AppDatabase.getInstance(applicationContext) }

    override fun onFailure(message: String) {
        Timber.w(message)
    }

    override fun getDataModel(): Flow<List<DataModel>> {
        return db.dataDao().get()
    }

    override fun setDataModel(data: List<DataModel>) {
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

    override suspend fun isData() : Boolean
    {
        return getDataModel().firstOrNull()?.isNotEmpty() == true
    }


//
//    override fun getDailyMotion(): Flow<List<DailyMotionPage>> {
//        return db.dailyMotionDao().get()
//    }
//
//    override fun setDailyMotion(data: DailyMotionPage) {
//        coroutinesScope.launch {
//            db.runInTransaction {
//
//                db.dailyMotionDao().apply {
//
//                    Timber.i("setDailyMotion: deleteAll")
//                    deleteAll()
//                    Timber.i("setDailyMotion: set")
//                    set(data)
//                }
//            }
//        }
//    }
//
//    override fun getGithub(): Flow<List<GithubPage>> {
//        return db.githubDao().get()
//    }
//
//    override fun setGithub(data: List<GithubPage>) {
//        coroutinesScope.launch {
//            db.runInTransaction {
//
//                db.githubDao().apply {
//
//                    Timber.i("setGithub: deleteAll")
//                    deleteAll()
//                    Timber.i("setGithub: set")
//                    set(data)
//                }
//            }
//        }
//    }
}