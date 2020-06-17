package com.einfoplanet.demo.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.einfoplanet.demo.adapter.CartProductListAdapter
import com.einfoplanet.demo.databinding.FragmentOrderPlacedBinding
import com.einfoplanet.demo.listeners.ButtonClickListener
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.AppConstants
import com.einfoplanet.demo.util.activityViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [OrderConfirmationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderConfirmationFragment : Fragment(), MainNavigationFragment {
    private lateinit var binding: FragmentOrderPlacedBinding
    private var orderId: String = ""
    private var strEDD: String = ""
    private lateinit var buttonClick: ButtonClickListener
    private lateinit var viewModel: CartViewModel
    private lateinit var cartProductsListAdapter: CartProductListAdapter

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
                strEDD = it.getString(AppConstants.ESTIMATED_DATE_OF_DELIVERY)!!
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = activityViewModelProvider()
        // Inflate the layout for this fragment
        binding = FragmentOrderPlacedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@OrderConfirmationFragment
        }

        binding.txtOrderId.text = orderId
        binding.txtDeliveryDate.text = strEDD
        binding.ivClose.setOnClickListener {
            viewModel.clearCart()
            buttonClick.closeButtonClick()
        }

        cartProductsListAdapter = CartProductListAdapter(viewModel, emptyList(), false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            adapter = cartProductsListAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.cartProductsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
                cartProductsListAdapter.setList(it)
            } else {
                binding.recyclerView.visibility = View.GONE
            }
        })
        viewModel.cartProductsLiveData
    }

    companion object {

        fun newInstance(orderId: String, strEDD: String): OrderConfirmationFragment {

            val args = Bundle().apply {
                putString(AppConstants.UNIQUE_ORDER_ID, orderId)
                putString(AppConstants.ESTIMATED_DATE_OF_DELIVERY, strEDD)
            }
            return OrderConfirmationFragment().apply { arguments = args }

        }
    }
}