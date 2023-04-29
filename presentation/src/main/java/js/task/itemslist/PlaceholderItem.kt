package js.task.itemslist



data class PlaceholderItem(val id: Int,
                           val userName: String,
                           val url: String,
                           val apiName: String,
                           val isSelected: Boolean = false)
{
    override fun toString(): String = userName
}