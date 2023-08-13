package js.task.di;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import js.task.domain.interfaces.IDataProvider;
import js.task.domain.usecase.GetDataUseCase;
import js.task.domain.usecase.interfaces.IGetDataUseCase;
import javax.inject.Named;


@Module
public class DataViewModelModule
{
    @Provides
    public IGetDataUseCase getDataUseCase(
        @Named("iDataProvider")
        IDataProvider iDataProvider,
        @Named("compositeDisposable")
        CompositeDisposable compositeDisposable
    )
    {
        return new GetDataUseCase(
                iDataProvider,
                compositeDisposable
        );
    }
}