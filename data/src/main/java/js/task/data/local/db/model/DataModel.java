package js.task.data.local.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import js.task.data.remote.retrofit.converter.model.ApiName;


@Entity(tableName = "data")
public class DataModel
{
    @Expose(
            serialize = false,
            deserialize = false
    )
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
            name = "id_auto"
    )
    public Integer idAuto;

    @ColumnInfo(name = "id")
    Integer id;

    @ColumnInfo(name = "userName")
    String userName;

    @ColumnInfo(name = "imageUrl")
    String imageUrl;

    @ColumnInfo(name = "apiName")
    ApiName apiName;

    public Integer getId()
    {
        return id;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public ApiName getApiName()
    {
        return apiName;
    }

    public DataModel(
            Integer id,
            String userName,
            String imageUrl,
            ApiName apiName
    )
    {
        this.id = id;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.apiName  = apiName;
    }
}
