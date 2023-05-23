package js.task.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import js.task.data.remote.retrofit.converter.model.ApiName

@Entity(tableName = "data")
data class DataModel
(
    @Expose(serialize = false, deserialize = false)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(
            name = "id_auto"
    ) val idAuto : Int? = null,

    @ColumnInfo(name = "id") val id : Int? = null,
    @ColumnInfo(name = "userName") val userName : String? = null,
    @ColumnInfo(name = "imageUrl") val imageUrl : String? = null,
    @ColumnInfo(name = "apiName") val apiName : ApiName? = null
)
