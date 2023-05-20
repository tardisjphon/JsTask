package js.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import js.task.data.db.model.DataModel
import js.task.di.DaggerApplicationGraph
import js.task.di.DataProviderModule
import js.task.di.DataViewModelModule
import js.task.di.GetDataUseCaseModule
import js.task.domain.model.DataResponse
import js.task.domain.usecase.DataUseCase
import js.task.provider.DataProvider
import javax.inject.Inject


class DataViewModel @Inject constructor(

    private val dataProvider: DataProvider,
    private val delegateObject: DataUseCase
)
    : ViewModel(), DataUseCase by delegateObject
{
    val dataList by lazy { ArrayList<DataModel>() }
    val dataObserver by lazy { MutableLiveData<DataResponse>() }

    init {
        setDataListener()
    }

    private fun setDataListener() {
        delegateObject.onNewData(dataProvider, dataList) {
            dataObserver.postValue(it)
        }
    }

    fun getData()
    {
        delegateObject.getData(dataProvider, dataList){}
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val applicationContext = checkNotNull(extras[APPLICATION_KEY]).applicationContext

                val applicationGraph = DaggerApplicationGraph.builder()
                    .dataProviderModule(DataProviderModule(applicationContext))
                    .getDataUseCaseModule(GetDataUseCaseModule(applicationContext))
                    .dataViewModelModule(DataViewModelModule(applicationContext))
                    .build()
                val getDataProvider = applicationGraph.dataProvider()
                val getDataUseCase = applicationGraph.getDataUseCase()

                return DataViewModel(getDataProvider, getDataUseCase) as T
            }
        }
    }
}