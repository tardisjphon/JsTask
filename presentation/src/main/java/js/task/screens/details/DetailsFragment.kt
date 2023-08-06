package js.task.screens.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import js.task.application.databinding.FragmentDetailsBinding
import js.task.extensions.parcelable
import js.task.screens.details.model.ParcelableNames
import js.task.screens.main.list.model.PlaceholderItem


class DetailsFragment : Fragment()
{
    private var imageViewUrl : ImageView? = null
    private var textviewUser : TextView? = null
    private var textViewApiName : TextView? = null

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
    ) : View
    {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        textviewUser = binding.textviewTitle
        textViewApiName = binding.textviewApiName
        imageViewUrl = binding.image
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            if (it.containsKey(ParcelableNames.PARCELABLE_NAME_DETAILS))
            {
                it.parcelable<PlaceholderItem>(ParcelableNames.PARCELABLE_NAME_DETAILS)?.let { item ->
                    bindingData(item)
                }
            }
        }
    }

    private fun bindingData(item: PlaceholderItem)
    {
        textviewUser?.text = item.userName
        textViewApiName?.text = item.apiName
        imageViewUrl?.let { imageView ->
            Glide.with(imageView.context).load(item.url).apply {
                into(imageView)
            }
        }
    }
}