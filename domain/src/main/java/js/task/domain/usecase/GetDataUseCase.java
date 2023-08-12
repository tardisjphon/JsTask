package js.task.domain.usecase;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import js.task.domain.interfaces.IDataProvider;
import js.task.domain.usecase.interfaces.IGetDataUseCase;
import js.task.domain.usecase.model.DomainModel;


public class GetDataUseCase
        implements IGetDataUseCase
{
    private final IDataProvider iDataProvider;
    private final CompositeDisposable compositeDisposable;

    public GetDataUseCase(
            IDataProvider iDataProvider,
            CompositeDisposable compositeDisposable
    )
    {
        this.iDataProvider = iDataProvider;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void invoke(MutableLiveData<List<DomainModel>> dataOut)
    {
        compositeDisposable.add(iDataProvider.getData()
                .subscribe(data ->
                {
                    if (data.isEmpty())
                    {
                        iDataProvider.download();
                    }
                    else
                    {
                        dataOut.postValue(data);
                    }
                }));
    }
}