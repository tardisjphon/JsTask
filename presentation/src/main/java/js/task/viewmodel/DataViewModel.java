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

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import js.task.App;
import js.task.domain.usecase.interfaces.IGetDataUseCase;
import js.task.domain.usecase.model.DomainModel;
import kotlin.Lazy;


public class DataViewModel extends ViewModel
{
    private final IGetDataUseCase getDataUseCase;

    private final Lazy<MutableLiveData<List<DomainModel>>> dataList = kotlin.LazyKt.lazy(MutableLiveData::new);

    public @Inject DataViewModel(IGetDataUseCase getLocalDataUseCase)
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

    @Override
    protected void onCleared()
    {
        if (!DataViewModelCompanion.compositeDisposable.getValue().isDisposed()) {
            DataViewModelCompanion.compositeDisposable.getValue().dispose();
        }
        super.onCleared();
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

                App app = (App)applicationContext;
                IGetDataUseCase iGetDataUseCase = app.getApplicationGraph().dataUseCase();
                return (T) new DataViewModel(iGetDataUseCase);
            }
        };
    }
}
