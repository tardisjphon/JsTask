package js.task.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import js.task.domain.usecase.interfaces.IGetDataUseCase
import js.task.domain.usecase.model.DomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


class MainViewModel constructor(
    private val getDataUseCase : IGetDataUseCase
) : ViewModel()
{
    val dataList : Flow<List<DomainModel>> by lazy { MutableSharedFlow() }

    fun getData() =
        viewModelScope.launch {
            getDataUseCase.invoke(dataList)
        }
}