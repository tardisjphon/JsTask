package js.task.domain

import js.task.data.db.model.DataModel
import js.task.data.net.utils.NetworkStatus
import js.task.domain.model.GetDataResponse
import js.task.domain.model.DataResponse
import js.task.domain.usecase.DataUseCase
import js.task.provider.DataProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


class GetDataUseCase @Inject constructor(private val networkStatus: NetworkStatus,
                                         private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : DataUseCase
{

    override fun getData(dataProvider: DataProvider,
                         dataList: ArrayList<DataModel>,
                         onResponse: (GetDataResponse) -> Unit)
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
                            onResponse(GetDataResponse.DOWNLOAD)
                        }

                        dataProvider.download()
                    }
                    else
                    {
                        withContext(Dispatchers.Main) {
                            onResponse(GetDataResponse.OFFLINE)
                        }

                        Timber.w("offline, so can't get data from internet")
                    }
                }
            }
        }
        else
        {
            CoroutineScope(Dispatchers.Main).launch {
                onResponse(GetDataResponse.NOT_CHANGED)
            }
        }
    }

    override fun onNewData(dataProvider: DataProvider,
                           dataList: ArrayList<DataModel>,
                           onResponse: (DataResponse) -> Unit)
    {
        CoroutineScope(Dispatchers.IO).launch {
            dataProvider.getData().collect { data ->

                if (data == dataList) {

                    Timber.i("the same data list")
                    responseOnMainThread(onResponse, DataResponse.REPO_NOT_CHANGED)
                    return@collect
                }

                dataList.clear()
                dataList.addAll(data)

                Timber.i("data list added")
                responseOnMainThread(onResponse, DataResponse.LOADED_FROM_REPO)
            }
        }
    }

    private fun responseOnMainThread(onResponse: (DataResponse) -> Unit,
                                     response: DataResponse)
    {
        CoroutineScope(Dispatchers.Main).launch {
            onResponse(response)
        }
    }
}
