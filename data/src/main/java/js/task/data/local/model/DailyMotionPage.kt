package js.task.data.local.model

data class DailyMotionPage
(
    val page : Int?,
    val limit : Int?,
    val explicit : Boolean?,
    val total : Int?,
    val has_more : Boolean?,
    val list : List<DailyMotion?>?,
)