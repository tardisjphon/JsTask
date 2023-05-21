package js.task.domain

import js.task.data.db.model.DataModel
import js.task.data.net.utils.NetworkStatus
import js.task.domain.model.GetDataResponse
import js.task.domain.usecase.GetDataUseCase
import js.task.provider.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class GetDataUseCaseImpl @Inject constructor(
    private val dataProvider : DataProvider,
    private val networkStatus : NetworkStatus,
    private val coroutineScope : CoroutineScope = CoroutineScope(Dispatchers.IO)
) : GetDataUseCase
{
    override suspend fun invokeGetData(dataList : ArrayList<DataModel>) : GetDataResponse
    {
        if (dataList.size == 0)
        {
            coroutineScope.launch {

                if (dataProvider.isRepositoryData())
                {
                    Timber.i("get data from repository")
                }
                else
                {
                    if (networkStatus.isOnline())
                    {
                        Timber.i("get data from internet")

                        withContext(Dispatchers.Main) {
                            return@withContext (GetDataResponse.DOWNLOAD)
                        }

                        dataProvider.download()
                    }
                    else
                    {
                        withContext(Dispatchers.Main) {
                            return@withContext (GetDataResponse.OFFLINE)
                        }

                        Timber.w("offline, so can't get data from internet")
                    }
                }
            }
        }

        return (GetDataResponse.NOT_CHANGED)
    }
}