package js.task.domain.interfaces;

import java.util.List;
import js.task.domain.usecase.model.DomainModel;
import io.reactivex.Observable;


public interface IDataProvider
{
    void download();
    Observable<List<DomainModel>> getData();
}