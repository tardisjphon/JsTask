package js.task.di;

import dagger.Component;
import js.task.data.di.DataProviderModule;
import js.task.domain.usecase.interfaces.IGetDataUseCase;


@Component(modules = {DataProviderModule.class, DataViewModelModule.class})
public interface ApplicationGraph
{
    IGetDataUseCase dataUseCase();
}