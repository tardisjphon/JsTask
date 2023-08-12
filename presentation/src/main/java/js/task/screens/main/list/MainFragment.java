package js.task.screens.main.list;

import static org.koin.java.KoinJavaComponent.inject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import js.task.application.databinding.FragmentListBinding;
import js.task.domain.usecase.model.DomainModel;
import js.task.screens.main.MainScreen;
import js.task.screens.main.list.adapter.ItemsListViewAdapter;
import js.task.screens.main.list.model.PlaceholderItem;
import js.task.viewmodel.DataViewModel;
import kotlin.Lazy;
import timber.log.Timber;


public class MainFragment
        extends Fragment
{
    private RecyclerView recyclerView;
    private List<PlaceholderItem> placeHolderItems;
    private final Lazy<DataViewModel> viewModel = inject(DataViewModel.class);

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    )
    {
        FragmentListBinding binding = FragmentListBinding.inflate(inflater, container, false);

        recyclerView = binding.itemList;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    )
    {
        super.onViewCreated(view, savedInstanceState);

        setObservers();

        getDataViewModel().getData();
    }

    private void setObservers()
    {
        getDataViewModel().getDataList()
                .observe(getViewLifecycleOwner(), data ->
                {
                    placeHolderItems = getPlaceholderItems(data);
                    setRecyclerView(recyclerView, placeHolderItems);
                    setDetailsFragment(placeHolderItems);
                });
    }

    private List<PlaceholderItem> getPlaceholderItems(List<DomainModel> dataList)
    {
        return dataList.stream()
                .map(mapping -> new PlaceholderItem(mapping.getId(), mapping.getUserName(), mapping.getImageUrl(), mapping.getApiName(), false))
                .collect(Collectors.toList());
    }

    private void setRecyclerView(
            RecyclerView recyclerView,
            List<PlaceholderItem> placeHolderItems
    )
    {
        Consumer<PlaceholderItem> callBack = placeholderItem ->
        {
            Optional<PlaceholderItem> optional = placeHolderItems.stream()
                    .filter(it -> it.getId() == placeholderItem.getId() && it.getApiName()
                            .equals(placeholderItem.getApiName()))
                    .findFirst();

            optional.ifPresent(this::goToDetails);
        };

        recyclerView.setAdapter(new ItemsListViewAdapter(placeHolderItems, callBack));
    }

    private void setDetailsFragment(List<PlaceholderItem> placeHolderItems)
    {
        Optional<PlaceholderItem> item = placeHolderItems.stream()
                .filter(PlaceholderItem::isSelected)
                .findFirst();

        item.ifPresent(this::goToDetails);

        if (!item.isPresent())
        {
            Timber.w("setDetailsFragment not set");
        }
    }

    private void goToDetails(PlaceholderItem item)
    {
        if (getActivity() != null)
        {
            ((MainScreen) getActivity()).goToDetails(item);
        }
    }

    private DataViewModel getDataViewModel()
    {
        return viewModel.getValue(); //.get(DataViewModel.class);
    }
}