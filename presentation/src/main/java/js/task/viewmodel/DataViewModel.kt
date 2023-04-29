package js.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import js.task.data.db.model.DataModel
import js.task.di.DaggerApplicationGraph
import js.task.di.DataProviderModule
import js.task.di.GetDataUseCaseModule
import js.task.domain.model.DataResponse
import js.task.domain.usecase.DataUseCase



class DataViewModel constructor(private val delegateObject: DataUseCase) : ViewModel(), DataUseCase by delegateObject
{
    val dataList by lazy { ArrayList<DataModel>() }
    val dataObserver by lazy { MutableLiveData<DataResponse>() }

    init {
        setDataListener()
    }

    private fun setDataListener() {
        delegateObject.onNewData(dataList) {
            dataObserver.postValue(it)
        }
    }

    fun getData()
    {
        delegateObject.getData(dataList){}
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val applicationContext = checkNotNull(extras[APPLICATION_KEY]).applicationContext

                val getDataUseCase = DaggerApplicationGraph.builder()
                    .getDataUseCaseModule(GetDataUseCaseModule(applicationContext))
                    .dataProviderModule(DataProviderModule(applicationContext))
                    .build().getDataUseCase()

                return DataViewModel(getDataUseCase) as T
            }
        }
    }
}