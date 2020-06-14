package com.einfoplanet.demo.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.einfoplanet.demo.databinding.FragmentOrderDetailsBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.AppConstants.Companion.SELECTED_RESTAURANT_ID
import com.einfoplanet.demo.util.activityViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [OrderDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderDetailsFragment : Fragment(), MainNavigationFragment {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: FragmentOrderDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activityViewModelProvider()
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OrderDetailsFragment
            viewModel = this@OrderDetailsFragment.viewModel
        }

        arguments?.let {
            val bundle: Bundle = arguments as Bundle
            bundle.let {
//                viewModel.selectedResId = it.getString(SELECTED_RESTAURANT_ID)!!
            }
        }

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {

    }

    companion object {

        fun newInstance(restaurantId: String) =
                OrderDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putString(SELECTED_RESTAURANT_ID, restaurantId)
                    }
                }
    }
}