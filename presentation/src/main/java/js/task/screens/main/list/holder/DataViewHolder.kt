package js.task.screens.main.list.holder

import android.annotation.SuppressLint
import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import js.task.application.databinding.ListItemBinding
import js.task.screens.main.list.model.PlaceholderItem

class DataViewHolder(private val binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    @SuppressLint("CheckResult")
    fun onBindData(item : PlaceholderItem, callBack : (PlaceholderItem) -> Unit)
    {
        binding.item = item
        binding.click = OnClickListener {
            callBack(item)
        }
    }
}