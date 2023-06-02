package js.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import js.task.di.*
import js.task.domain.usecase.model.DataResponse
import js.task.domain.usecase.GetDataUseCase
import js.task.domain.usecase.OnNewDataUseCase
import js.task.domain.usecase.model.DomainModel
import js.task.data.di.DataProviderModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject


class DataViewModel @Inject constructor(
    private val delegateGetDataUseCase : GetDataUseCase,
    private val delegateOnNewDataUseCase : OnNewDataUseCase
) : ViewModel(), GetDataUseCase by delegateGetDataUseCase,
    OnNewDataUseCase by delegateOnNewDataUseCase
{
    val dataList by lazy { ArrayList<DomainModel>() }
    val dataObserver by lazy { MutableLiveData<DataResponse>() }

    //https://medium.com/androiddevelopers/consuming-flows-safely-in-jetpack-compose-cde014d0d5a3

//    val users = flowOf()
//        .execute()
//        .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5000L),
//                initialValue = emptyList()
//        )

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
                    .getDataUseCaseModule(GetDataUseCaseModule())
                    .dataViewModelModule(DataViewModelModule()).build()
                val getDataUseCase = applicationGraph.getDataUseCase()
                val onNewDataUseCase = applicationGraph.onNewDataUseCase()

                return DataViewModel(getDataUseCase, onNewDataUseCase) as T
            }
        }
    }
}