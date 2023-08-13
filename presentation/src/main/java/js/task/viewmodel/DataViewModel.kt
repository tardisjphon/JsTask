package js.task.viewmodel

import androidx.lifecycle.ViewModel
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.MutableSharedFlow


class DataViewModel constructor(
    private val getDataUseCase : IGetDataUseCase
) : ViewModel()
{
    val dataList by lazy { MutableSharedFlow<List<DomainModel>>() }

    fun getData()
    {
        getDataUseCase.invoke(dataList)
    }
}