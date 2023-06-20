package js.task.screens.main.list.holder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.function.Consumer;
import js.task.application.databinding.ListItemBinding;
import js.task.screens.main.list.model.PlaceholderItem;


public class DataViewHolderJava
        extends RecyclerView.ViewHolder
{
    private final ListItemBinding binding;

    public DataViewHolderJava(@NonNull ListItemBinding binding)
    {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBindData(
            PlaceholderItem item,
            Consumer<PlaceholderItem> callBack
    )
    {
        binding.setItem(item);
        binding.setClick(v -> callBack.accept(item));
    }
}
