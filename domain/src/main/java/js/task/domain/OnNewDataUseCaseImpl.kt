package js.task.domain

import js.task.data.db.model.DataModel
import js.task.domain.model.DataResponse
import js.task.domain.usecase.OnNewDataUseCase
import js.task.provider.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class OnNewDataUseCaseImpl @Inject constructor(
    private val dataProvider : DataProvider,
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
) : OnNewDataUseCase
{
    override suspend fun invokeOnNewData(dataList : ArrayList<DataModel>) : Flow<DataResponse>
    {
        return withContext(coroutineScope.coroutineContext) {

            dataProvider.getData().map { data ->

                if (data == dataList)
                {
                    Timber.i("the same data list")

                    (DataResponse.REPO_NOT_CHANGED)
                }

                dataList.clear()
                dataList.addAll(data)

                Timber.i("data list added")
                (DataResponse.LOADED_FROM_REPO)
            }
        }
    }
}