package js.task.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import js.task.application.R
import js.task.application.databinding.FragmentListBinding
import js.task.data.db.model.DataModel
import js.task.domain.model.DataResponse
import js.task.views.itemslist.ItemsListViewAdapter
import js.task.views.itemslist.PlaceholderItem
import js.task.views.model.RecyclerData
import js.task.viewmodel.DataViewModel
import timber.log.Timber

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var recyclerView: RecyclerView ?= null
    private var placeHolderItems: List<PlaceholderItem> ?= null

    private val viewModel: DataViewModel by viewModels { DataViewModel.Factory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        recyclerView = binding.itemList
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        viewModel.getData()
    }

    private fun setObservers() {
        viewModel.dataObserver.observe(viewLifecycleOwner) { response ->

            if (placeHolderItems == null || response == DataResponse.LOADED_FROM_REPO) {
                placeHolderItems = getPlaceholderItems(viewModel.dataList)
            }

            if (recyclerView?.adapter == null || response == DataResponse.LOADED_FROM_REPO) {
                setRecyclerView(recyclerView, placeHolderItems)
            }

            when (response)
            {
                DataResponse.REPO_NOT_CHANGED -> {
                    Timber.i("repo not changed")
                }
                DataResponse.LOADED_FROM_REPO -> {
                    placeHolderItems?.let {
                        setDetailsFragment(placeHolderItems)
                    }
                }
                else -> {}
            }
        }
    }

    private fun setRecyclerView(recyclerView: RecyclerView?, placeHolderItems: List<PlaceholderItem>?)
    {
        Timber.i("setRecyclerView")

        if (placeHolderItems == null) {
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

    private fun setDetailsFragment(placeHolderItems: List<PlaceholderItem>?)
    {
        Timber.i("setDetailsFragment")
        placeHolderItems?.firstOrNull { it.isSelected }?.let {
            goToDetails(it)
        } ?: { Timber.w("setDetailsFragment not set") }
    }

    private fun getPlaceholderItems(dataList: ArrayList<DataModel>) : List<PlaceholderItem>
    {
        val placeHolderItems = ArrayList<PlaceholderItem>()
        dataList.forEach {
            placeHolderItems.add(
                PlaceholderItem(it.id ?: 0,
                it.userName ?: "",
                it.imageUrl ?: "",
                it.apiName?.name ?: "")
            )
        }
        return placeHolderItems
    }

    private fun goToDetails(item: PlaceholderItem)
    {
        val bundle = Bundle()
        bundle.putString(
            RecyclerData.ARGUMENTS_USER_NAME,
            item.userName
        )
        bundle.putString(
            RecyclerData.ARGUMENTS_URL,
            item.url
        )
        bundle.putString(
            RecyclerData.ARGUMENTS_API,
            item.apiName
        )
        val navController = (activity as MainActivity).getNavControllerDetails()
        navController.navigate(R.id.action_to_DetailsFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}