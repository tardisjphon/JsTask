package js.task.data

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import js.task.data.local.DbRepository
import js.task.data.local.db.model.DataModel
import js.task.data.remote.RetrofitRepository
import javax.inject.Inject


class DataProvider @Inject constructor(
    private val compositeDisposable : CompositeDisposable,
    private val localRepository : DbRepository,
    private val remoteRepository : RetrofitRepository
)
{

    fun download()
    {
        compositeDisposable.addAll(remoteRepository.get()
            .subscribe {
                localRepository.updateData(it)
            })
    }

    fun getData() : Observable<List<DataModel>>
    {
        return localRepository.getData()
    }
}