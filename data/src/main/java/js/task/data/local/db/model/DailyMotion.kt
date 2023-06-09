package js.task.data.local.db.model

import com.google.gson.annotations.SerializedName


data class DailyMotion
(
    val id : String? = null,
    @SerializedName("screenname")
    val screenName : String? = null
)
