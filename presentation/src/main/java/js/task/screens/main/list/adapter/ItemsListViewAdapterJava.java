package js.task.screens.main.list.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.function.Consumer;
import js.task.application.databinding.ListItemBinding;
import js.task.screens.main.list.holder.DataViewHolderJava;
import js.task.screens.main.list.model.PlaceholderItem;


public class ItemsListViewAdapterJava
        extends RecyclerView.Adapter<DataViewHolderJava>
{
    private final List<PlaceholderItem> data;
    private final Consumer<PlaceholderItem> callBack;

    public ItemsListViewAdapterJava(
            List<PlaceholderItem> data,
            Consumer<PlaceholderItem> callBack
    )
    {
        this.data = data;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public DataViewHolderJava onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    )
    {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DataViewHolderJava(binding);
    }

    @Override
    public void onBindViewHolder(
            @NonNull DataViewHolderJava holder,
            int position
    )
    {
        holder.onBindData(data.get(position), callBack);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }
}