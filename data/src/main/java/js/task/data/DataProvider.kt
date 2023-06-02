package js.task.data

import js.task.data.local.DbRepository
import js.task.data.local.db.model.DataModel
import js.task.data.remote.RetrofitRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DataProvider @Inject constructor(
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val localRepository : DbRepository,
    private val remoteRepository : RetrofitRepository
)
{
    fun download()
    {
        coroutineScope.launch {
            remoteRepository.get()
                .collect {
                    localRepository.updateData(it)
                }
        }
    }

    fun getData() : Flow<List<DataModel>>
    {
        return localRepository.getData()
    }
}