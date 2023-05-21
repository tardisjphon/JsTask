package js.task.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import js.task.application.databinding.FragmentDetailsBinding
import js.task.views.model.RecyclerData.Companion.ARGUMENTS_API
import js.task.views.model.RecyclerData.Companion.ARGUMENTS_URL
import js.task.views.model.RecyclerData.Companion.ARGUMENTS_USER_NAME


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var textviewUrl: TextView ?= null
    private var textviewUser: TextView ?= null
    private var textViewApiName: TextView ?= null

    private var itemUrl: String = ""
    private var itemUser: String = ""
    private var itemApiName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        textviewUser = binding.textviewTitle
        textviewUrl = binding.textviewDetails
        textViewApiName = binding.textviewApiName
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            if (it.containsKey(ARGUMENTS_USER_NAME)) {
                itemUser = it.getString(ARGUMENTS_USER_NAME) ?: ""
                itemUrl = it.getString(ARGUMENTS_URL) ?: ""
                itemApiName = it.getString(ARGUMENTS_API) ?: ""
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textviewUser?.text = itemUser
        textviewUrl?.text = itemUrl.ifBlank { "no url" }
        textViewApiName?.text = itemApiName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}