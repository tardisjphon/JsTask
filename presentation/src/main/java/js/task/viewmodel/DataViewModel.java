package js.task.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import js.task.data.di.DataProviderModule;
import js.task.di.ApplicationGraph;
import js.task.di.DaggerApplicationGraph;
import js.task.di.DataViewModelModule;
import js.task.di.GetDataUseCaseModule;
import js.task.domain.usecase.IGetLocalDataUseCase;
import js.task.domain.usecase.IGetRemoteDataUseCase;
import js.task.domain.usecase.model.DomainModel;
import kotlin.Lazy;


/**
 * https://kotlinlang.org/docs/java-to-kotlin-interop.html#instance-fields
 */

public class DataViewModel
        extends ViewModel
        implements IGetLocalDataUseCase,
                   IGetRemoteDataUseCase
{
    private final IGetLocalDataUseCase getLocalDataUseCase;
    private final IGetRemoteDataUseCase getRemoteDataUseCase;
    private final Lazy<MutableLiveData<List<DomainModel>>> dataList = kotlin.LazyKt.lazy(MutableLiveData::new);

    public @Inject DataViewModel(IGetLocalDataUseCase getLocalDataUseCase,
                                 IGetRemoteDataUseCase getRemoteDataUseCase)
    {
        this.getLocalDataUseCase = getLocalDataUseCase;
        this.getRemoteDataUseCase = getRemoteDataUseCase;
    }

    public void getData()
    {
        DataViewModelCompanion.compositeDisposable.getValue().addAll(getLocalDataUseCase.invokeLocal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.isEmpty())
                    {
                        getRemoteDataUseCase.invokeRemote();
                    }
                    else
                    {
                        dataList.getValue().postValue(data);
                    }
                })
        );
    }

    public MutableLiveData<List<DomainModel>> getDataList()
    {
        return dataList.getValue();
    }

    @Override
    protected void onCleared()
    {
        if (!DataViewModelCompanion.compositeDisposable.getValue().isDisposed()) {
            DataViewModelCompanion.compositeDisposable.getValue().dispose();
        }
        super.onCleared();
    }

    @NonNull
    @Override
    public Observable<List<DomainModel>> invokeLocal()
    {
        return getLocalDataUseCase.invokeLocal();
    }

    @Override
    public void invokeRemote()
    {
        getRemoteDataUseCase.invokeRemote();
    }

    public static final class DataViewModelCompanion
    {
        final static Lazy<CompositeDisposable> compositeDisposable = kotlin.LazyKt.lazy(CompositeDisposable::new);

        public final static ViewModelProvider.Factory Factory = new ViewModelProvider.Factory()
        {
            @SuppressWarnings("unchecked")
            @NonNull
            @Override
            public <T extends ViewModel> T create(
                    @NonNull Class<T> modelClass,
                    @NonNull CreationExtras extras
            )
            {
                Application application = extras.get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                Context applicationContext = Objects.requireNonNull(application)
                        .getApplicationContext();


                ApplicationGraph applicationGraph = DaggerApplicationGraph.builder()
                        .dataProviderModule(new DataProviderModule(applicationContext, compositeDisposable.getValue()))
                        .getDataUseCaseModule(new GetDataUseCaseModule())
                        .dataViewModelModule(new DataViewModelModule(compositeDisposable.getValue()))
                        .build();

                IGetLocalDataUseCase getLocalDataUseCase = applicationGraph.getLocalDataUseCase();
                IGetRemoteDataUseCase getRemoteDataUseCase = applicationGraph.getRemoteDataUseCase();

                return (T) new DataViewModel(
                        getLocalDataUseCase,
                        getRemoteDataUseCase
                );
            }
        };
    }
}
