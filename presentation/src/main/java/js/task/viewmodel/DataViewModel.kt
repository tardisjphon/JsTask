package js.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import js.task.data.di.DataProviderModule
import js.task.di.*
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.domain.usecase.model.DomainModel
import javax.inject.Inject


class DataViewModel @Inject constructor(
    private val getDataUseCase : IGetDataUseCase
) : ViewModel()
{
    val dataList by lazy { MutableLiveData<List<DomainModel>>() }

    fun getData()
    {
        getDataUseCase.invoke(dataList)
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
                    .dataViewModelModule(DataViewModelModule())
                    .build()
                val getDataUseCase = applicationGraph.dataUseCase()

                return DataViewModel(getDataUseCase) as T
            }
        }
    }
}