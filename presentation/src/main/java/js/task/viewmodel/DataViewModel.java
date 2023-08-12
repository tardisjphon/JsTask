package js.task.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import js.task.domain.usecase.interfaces.IGetDataUseCase;
import js.task.domain.usecase.model.DomainModel;
import kotlin.Lazy;


public class DataViewModel extends ViewModel
{
    private final IGetDataUseCase getDataUseCase;

    private final Lazy<MutableLiveData<List<DomainModel>>> dataList = kotlin.LazyKt.lazy(MutableLiveData::new);

    public DataViewModel(IGetDataUseCase getLocalDataUseCase)
    {
        this.getDataUseCase = getLocalDataUseCase;
    }

    public void getData()
    {
        getDataUseCase.invoke(dataList.getValue());
    }

    public MutableLiveData<List<DomainModel>> getDataList()
    {
        return dataList.getValue();
    }
}
