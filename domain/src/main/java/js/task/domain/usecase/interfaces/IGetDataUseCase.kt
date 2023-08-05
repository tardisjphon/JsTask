package js.task.domain.usecase.interfaces

import androidx.lifecycle.MutableLiveData
import js.task.domain.usecase.model.DomainModel


interface IGetDataUseCase
{
    fun invoke(data: MutableLiveData<List<DomainModel>>) {}
}