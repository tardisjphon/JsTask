package js.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import js.task.data.di.DataProviderModule
import js.task.di.*
import js.task.domain.usecase.IGetLocalDataUseCase
import js.task.domain.usecase.IGetRemoteDataUseCase
import js.task.domain.usecase.model.DomainModel
import javax.inject.Inject


class DataViewModel @Inject constructor(
    private val getLocalDataUseCase : IGetLocalDataUseCase,
    private val getRemoteDataUseCase : IGetRemoteDataUseCase
) : ViewModel(), IGetLocalDataUseCase by getLocalDataUseCase,
    IGetRemoteDataUseCase by getRemoteDataUseCase
{
    val dataList by lazy { MutableLiveData<List<DomainModel>>() }

    fun getData()
    {
        compositeDisposable.addAll(getLocalDataUseCase.invokeLocal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.isEmpty())
                    {
                        getRemoteDataUseCase.invokeRemote()
                    }
                    else
                    {
                        dataList.postValue(it)
                    }
                })
    }

    companion object
    {
        private val compositeDisposable by lazy { CompositeDisposable() }

        @JvmField
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory
        {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass : Class<T>, extras : CreationExtras
            ) : T
            {
                val applicationContext = checkNotNull(extras[APPLICATION_KEY]).applicationContext

                val applicationGraph = DaggerApplicationGraph.builder()
                    .dataProviderModule(DataProviderModule(applicationContext, compositeDisposable))
                    .getDataUseCaseModule(GetDataUseCaseModule())
                    .dataViewModelModule(DataViewModelModule(compositeDisposable))
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

    override fun onCleared()
    {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onCleared()
    }
}