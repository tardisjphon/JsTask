package js.task.data.local

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import js.task.data.Repository
import js.task.data.local.db.AppDatabase
import js.task.data.local.db.model.DataModel
import javax.inject.Inject


class DbRepository @Inject constructor(
    private val db : AppDatabase,
    private val compositeDisposable : CompositeDisposable
) : Repository
{
    override fun getData() : Observable<List<DataModel>>
    {
        return db.dataDao()
            .get()
    }

    override fun setData(data : List<DataModel>)
    {
        val disposable = Observable.empty<Void>()
            .observeOn(Schedulers.computation())
            .doOnComplete {
                db.runInTransaction {
                    db.dataDao()
                        .apply {
                            println("DataModel: deleteAll")
                            deleteAll()
                            println("DataModel: set")
                            set(data)
                        }
                }
            }
            .subscribe()
        compositeDisposable.addAll(disposable)
    }

    override fun updateData(data : List<DataModel>)
    {
        val disposable = Observable.empty<Void>()
            .observeOn(Schedulers.computation())
            .doOnComplete {
                db.runInTransaction {
                    db.dataDao()
                        .apply {
                            println("DataModel: set ${Thread.currentThread()}")
                            set(data)
                        }
                }
            }
            .subscribe()
        compositeDisposable.addAll(disposable)
    }
}