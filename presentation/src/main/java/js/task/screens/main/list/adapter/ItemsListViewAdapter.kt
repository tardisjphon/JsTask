package js.task.screens.main.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import js.task.application.databinding.ListItemBinding
import js.task.screens.main.list.holder.DataViewHolder
import js.task.screens.main.list.model.PlaceholderItem


open class ItemsListViewAdapter(
    private val data : List<PlaceholderItem>, private val callBack : (PlaceholderItem) -> Unit
) : RecyclerView.Adapter<DataViewHolder>()
{
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : DataViewHolder
    {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder : DataViewHolder, position : Int)
    {
        holder.onBindData(data[position], callBack)
    }
}