package js.task.domain.usecase;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
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