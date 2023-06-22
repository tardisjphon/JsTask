package js.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import js.task.data.di.DataProviderModule
import js.task.di.*
import js.task.domain.usecase.IGetLocalDataUseCase
import js.task.domain.usecase.IGetRemoteDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class DataViewModel @Inject constructor(
    private val getLocalDataUseCase : IGetLocalDataUseCase,
    private val getRemoteDataUseCase : IGetRemoteDataUseCase
) : ViewModel(), IGetLocalDataUseCase by getLocalDataUseCase,
    IGetRemoteDataUseCase by getRemoteDataUseCase
{

    fun getData()
    {
        CoroutineScope(Dispatchers.Main).launch {
            getLocalDataUseCase.invokeLocal()
                .collect {
                    if (it.isEmpty())
                    {
                        getRemoteDataUseCase.invokeRemote()
                    }
                }
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
                    .dataViewModelModule(DataViewModelModule())
                    .build()
                val getLocalDataUseCase = applicationGraph.getLocalDataUseCase()
                val getRemoteDataUseCase = applicationGraph.getRemoteDataUseCase()

                return DataViewModel(
                        getLocalDataUseCase,
                        getRemoteDataUseCase
                ) as T
            }
        }
    }
}