package js.task.itemslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import js.task.application.databinding.ListItemBinding


class ItemsListViewAdapter(
    private val data: List<PlaceholderItem>,
    private val callBack: (PlaceholderItem) -> Unit
) : RecyclerView.Adapter<DataViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int)
    {
        holder.onBindData(data[position], callBack)
    }
}