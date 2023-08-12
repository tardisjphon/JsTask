package js.task.data;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import js.task.data.local.db.model.DataModel;


public interface Repository
{
    Observable<List<DataModel>> getData();

    void updateData(List<DataModel> data);

    void setData(List<DataModel> data);
}
