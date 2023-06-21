package js.task.screens.details;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import js.task.application.databinding.FragmentDetailsBinding;
import js.task.screens.details.model.ParcelableNames;
import js.task.screens.main.list.model.PlaceholderItem;


public class DetailsScreenFragment extends Fragment
{
    private ImageView imageViewUrl;
    private TextView textviewUser;
    private TextView textViewApiName;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    )
    {
        FragmentDetailsBinding binding = FragmentDetailsBinding.inflate(inflater, container, false);
        textviewUser = binding.textviewTitle;
        textViewApiName = binding.textviewApiName;
        imageViewUrl = binding.image;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    )
    {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null)
        {
            if (getArguments().containsKey(ParcelableNames.PARCELABLE_NAME_DETAILS))
            {
                Parcelable parcelable = getArguments().getParcelable(ParcelableNames.PARCELABLE_NAME_DETAILS);
                if (parcelable instanceof PlaceholderItem item)
                {
                    bindingData(item);
                }
            }
        }
    }

    private void bindingData(PlaceholderItem item)
    {
        textviewUser.setText(item.getUserName());
        textViewApiName.setText(item.getApiName());
        Glide.with(imageViewUrl.getContext()).load(item.getUrl()).into(imageViewUrl);
    }
}
