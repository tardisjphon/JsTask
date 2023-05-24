package js.task.screens.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import js.task.application.R
import js.task.application.databinding.FragmentListBinding
import js.task.domain.usecase.model.DataResponse
import js.task.domain.usecase.model.DomainModel
import js.task.screens.details.model.ParcelableNames
import js.task.screens.main.MainActivity
import js.task.viewmodel.DataViewModel
import js.task.screens.main.list.adapter.ItemsListViewAdapter
import js.task.screens.main.list.model.PlaceholderItem
import timber.log.Timber

class ListFragment : Fragment()
{
    private var recyclerView : RecyclerView? = null
    private var placeHolderItems : List<PlaceholderItem>? = null

    private val viewModel : DataViewModel by viewModels { DataViewModel.Factory }


    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
    ) : View
    {
        val binding = FragmentListBinding.inflate(inflater, container, false)
        recyclerView = binding.itemList
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        viewModel.getData()
    }

    private fun setObservers()
    {
        viewModel.dataObserver.observe(viewLifecycleOwner) { response ->

            if (placeHolderItems == null || response == DataResponse.LOADED_FROM_REPO)
            {
                placeHolderItems = getPlaceholderItems(viewModel.dataList)
            }

            if (recyclerView?.adapter == null || response == DataResponse.LOADED_FROM_REPO)
            {
                setRecyclerView(recyclerView, placeHolderItems)
            }

            when (response)
            {
                DataResponse.REPO_NOT_CHANGED ->
                {
                    Timber.i("repo not changed")
                }
                DataResponse.LOADED_FROM_REPO ->
                {
                    placeHolderItems?.let {
                        setDetailsFragment(placeHolderItems)
                    }
                }
                else ->
                {
                }
            }
        }
    }

    private fun setRecyclerView(
        recyclerView : RecyclerView?, placeHolderItems : List<PlaceholderItem>?
    )
    {
        Timber.i("setRecyclerView")

        if (placeHolderItems == null)
        {
            Timber.w("placeHolderItems == null")
            return
        }

        recyclerView?.adapter = ItemsListViewAdapter(placeHolderItems) { item ->
            placeHolderItems.firstOrNull {
                it.id == item.id && it.apiName == item.apiName
            }?.let {
                goToDetails(it)
            }
        }
    }

    private fun setDetailsFragment(placeHolderItems : List<PlaceholderItem>?)
    {
        Timber.i("setDetailsFragment")
        placeHolderItems?.firstOrNull { it.isSelected }?.let {
            goToDetails(it)
        } ?: { Timber.w("setDetailsFragment not set") }
    }

    private fun getPlaceholderItems(dataList : ArrayList<DomainModel>) : List<PlaceholderItem>
    {
        val placeHolderItems = ArrayList<PlaceholderItem>()
        dataList.forEach {
            placeHolderItems.add(
                    PlaceholderItem(it.id, it.userName, it.imageUrl, it.apiName)
            )
        }
        return placeHolderItems
    }

    private fun goToDetails(item : PlaceholderItem)
    {

        val bundle =  Bundle().apply {
            putParcelable(ParcelableNames.PARCELABLE_NAME_DETAILS, item)
        }

        val navController = (activity as MainActivity).getNavControllerDetails()
        navController.navigate(R.id.action_to_DetailsFragment, bundle)
    }
}