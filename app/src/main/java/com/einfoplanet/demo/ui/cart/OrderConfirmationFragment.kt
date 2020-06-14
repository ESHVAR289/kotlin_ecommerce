package com.einfoplanet.demo.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.einfoplanet.demo.databinding.FragmentOrderPlacedBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.AppConstants
import com.einfoplanet.demo.util.activityViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [OrderConfirmationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderConfirmationFragment : Fragment(), MainNavigationFragment {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: FragmentOrderPlacedBinding
    private var orderId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val bundle: Bundle = arguments as Bundle
            bundle.let {
                orderId = it.getString(AppConstants.UNIQUE_ORDER_ID)!!
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activityViewModelProvider()
        binding = FragmentOrderPlacedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OrderConfirmationFragment
            viewModel = this@OrderConfirmationFragment.viewModel
        }

        binding.txtOrderId.text = orderId

        return binding.root
    }

    companion object {

        fun newInstance(orderId: String): OrderConfirmationFragment {

            val args = Bundle().apply {
                putString(AppConstants.UNIQUE_ORDER_ID, orderId)
            }
            return OrderConfirmationFragment().apply { arguments = args }

        }
    }
}