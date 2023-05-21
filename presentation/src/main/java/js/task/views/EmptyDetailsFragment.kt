package js.task.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import js.task.application.databinding.FragmentDetailsEmptyBinding


class EmptyDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailsEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }
}