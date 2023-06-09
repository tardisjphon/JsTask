package js.task.data.local.db.model

import com.google.gson.annotations.SerializedName

data class DailyMotionPage
(
    val page : Int?,
    val limit : Int?,
    val explicit : Boolean?,
    val total : Int?,
    @SerializedName("has_more")
    val hasMore : Boolean?,
    val list : List<DailyMotion?>?,
)