package js.task.data

import js.task.data.local.DbRepository
import js.task.data.local.db.model.DataModel
import js.task.data.remote.RetrofitRepository
import js.task.domain.interfaces.IDataProvider
import js.task.domain.usecase.model.DomainApiName
import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class DataProvider @Inject constructor(
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val localRepository : DbRepository,
    private val remoteRepository : RetrofitRepository
) : IDataProvider
{
    override fun download()
    {
        coroutineScope.launch {
            remoteRepository.get()
                .collect {
                    localRepository.updateData(it)
                }
        }
    }

    override fun getData() : Flow<List<DomainModel>>
    {
        return localRepository.getData().map(mapToDomain())
    }

    private fun mapToDomain() : (List<DataModel>) -> List<DomainModel>
    {
        val transform: (List<DataModel>) -> List<DomainModel> = {
            it.map {
                DomainModel(it.id,
                            it.userName,
                            it.imageUrl,
                            DomainApiName.valueOf(it.apiName?.name ?: ""))
            }
        }
        return transform
    }
}