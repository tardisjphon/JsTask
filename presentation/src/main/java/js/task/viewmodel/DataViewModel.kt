package js.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import js.task.data.db.model.DataModel
import js.task.di.*
import js.task.domain.model.DataResponse
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.OnNewDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DataViewModel @Inject constructor(
    val delegateGetDataUseCase : GetDataUseCase, val delegateOnNewDataUseCase : OnNewDataUseCase
) : ViewModel(), GetDataUseCase by delegateGetDataUseCase,
    OnNewDataUseCase by delegateOnNewDataUseCase
{
    val dataList by lazy { ArrayList<DataModel>() }
    val dataObserver by lazy { MutableLiveData<DataResponse>() }

    init
    {
        setDataListener()
    }

    private fun setDataListener()
    {
        CoroutineScope(Dispatchers.Main).launch {
            delegateOnNewDataUseCase.invokeOnNewData(dataList).collect {
                dataObserver.postValue(it)
            }
        }
    }

    fun getData()
    {
        CoroutineScope(Dispatchers.Main).launch {
            delegateGetDataUseCase.invokeGetData(dataList)
        }
    }


    companion object
    {

        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory
        {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass : Class<T>, extras : CreationExtras
            ) : T
            {
                val applicationContext = checkNotNull(extras[APPLICATION_KEY]).applicationContext

                val applicationGraph = DaggerApplicationGraph.builder()
                    .dataProviderModule(DataProviderModule(applicationContext))
                    .getDataUseCaseModule(GetDataUseCaseModule(applicationContext))
                    .dataViewModelModule(DataViewModelModule(applicationContext)).build()
                val getDataUseCase = applicationGraph.getDataUseCase()
                val onNewDataUseCase = applicationGraph.onNewDataUseCase()

                return DataViewModel(getDataUseCase, onNewDataUseCase) as T
            }
        }
    }
}