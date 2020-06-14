package com.einfoplanet.demo.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.einfoplanet.demo.databinding.FragmentOrderDetailsBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.AppConstants.Companion.SELECTED_RESTAURANT_ID
import com.einfoplanet.demo.util.activityViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [OrderConfirmationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderConfirmationFragment : Fragment(), MainNavigationFragment {

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
            lifecycleOwner = this@OrderConfirmationFragment
            viewModel = this@OrderConfirmationFragment.viewModel
        }

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {

    }

    companion object {

        fun newInstance(restaurantId: String) =
                OrderConfirmationFragment().apply {
                    arguments = Bundle().apply {
                        putString(SELECTED_RESTAURANT_ID, restaurantId)
                    }
                }
    }
}