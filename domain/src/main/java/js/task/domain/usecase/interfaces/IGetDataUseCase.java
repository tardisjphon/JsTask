package js.task.domain.usecase.interfaces;

import androidx.lifecycle.MutableLiveData;
import java.util.List;
import js.task.domain.usecase.model.DomainModel;


public interface IGetDataUseCase
{
    void invoke(MutableLiveData<List<DomainModel>> data);
}