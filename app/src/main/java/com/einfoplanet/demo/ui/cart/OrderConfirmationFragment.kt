package com.einfoplanet.demo.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.einfoplanet.demo.databinding.FragmentOrderPlacedBinding
import com.einfoplanet.demo.listeners.ButtonClickListener
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.AppConstants

/**
 * A simple [Fragment] subclass.
 * Use the [OrderConfirmationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderConfirmationFragment : Fragment(), MainNavigationFragment {
    private lateinit var binding: FragmentOrderPlacedBinding
    private var orderId: String = ""
    private lateinit var buttonClick: ButtonClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonClickListener) {
            buttonClick = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement listener");
        }
    }

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
        binding = FragmentOrderPlacedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OrderConfirmationFragment
        }

        binding.txtOrderId.text = orderId
        binding.ivClose.setOnClickListener {
            buttonClick.closeButtonClick()
        }

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