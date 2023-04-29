package js.task.itemslist

import android.annotation.SuppressLint
import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import js.task.application.databinding.ListItemBinding

class DataViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
{
    @SuppressLint("CheckResult")
    fun onBindData(item: PlaceholderItem, callBack: (PlaceholderItem) -> Unit)
    {
        binding.item = item

        binding.click = OnClickListener {
                callBack(item)
        }
    }
}