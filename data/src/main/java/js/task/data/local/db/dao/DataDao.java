package js.task.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Observable;
import js.task.data.local.db.model.DataModel;


@Dao
public interface DataDao
{
    @Query("SELECT * FROM data ORDER BY id ASC")
    Observable<List<DataModel>> get();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void set(List<DataModel> data);

    @Query("DELETE FROM data")
    void deleteAll();
}
