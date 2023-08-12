package js.task.data;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import js.task.data.local.DbRepository;
import js.task.data.local.db.model.DataModel;
import js.task.data.remote.RetrofitRepository;
import js.task.domain.interfaces.IDataProvider;
import js.task.domain.usecase.model.DomainModel;


public class DataProvider
        implements IDataProvider
{
    private final CompositeDisposable compositeDisposable;
    private final DbRepository localRepository;
    private final RetrofitRepository remoteRepository;

    public DataProvider(
            CompositeDisposable compositeDisposableInject,
            DbRepository localRepositoryInject,
            RetrofitRepository remoteRepositoryInject
    )
    {
        compositeDisposable = compositeDisposableInject;
        localRepository     = localRepositoryInject;
        remoteRepository    = remoteRepositoryInject;
    }

    @Override
    public void download()
    {
        compositeDisposable.addAll(remoteRepository.get()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                localRepository::updateData
        ));
    }

    @Override
    public Observable<List<DomainModel>> getData()
    {
        return localRepository.getData()
                .map(this::mapToDomain);
    }

    private List<DomainModel> mapToDomain(List<DataModel> dataList)
    {
        return dataList.stream()
                .map(data ->
                        new DomainModel(data.getId(), data.getUserName(), data.getImageUrl(), data.getApiName() == null || data.getApiName()
                        .name()
                        .isEmpty() ? "" : data.getApiName()
                        .name()))
                .collect(Collectors.toList());
    }
}
