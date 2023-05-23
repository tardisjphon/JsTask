package js.task.provider

import js.task.data.DbRepository
import js.task.data.RetrofitRepository
import js.task.data.local.model.DataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataProvider @Inject constructor(
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val repository : DbRepository,
    private val retrofit : RetrofitRepository
)
{
    fun download()
    {
        coroutineScope.launch {
            retrofit.get()
        }
    }

    fun getData() : Flow<List<DataModel>>
    {
        return repository.getData()
    }

    suspend fun isRepositoryData() : Boolean
    {
        return repository.isData()
    }
}