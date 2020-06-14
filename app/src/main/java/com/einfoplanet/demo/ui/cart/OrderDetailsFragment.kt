package com.einfoplanet.demo.ui.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.einfoplanet.demo.databinding.FragmentOrderDetailsBinding
import com.einfoplanet.demo.listeners.ButtonClickListener
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.activityViewModelProvider
import kotlinx.android.synthetic.main.fragment_order_details.*

/**
 * A simple [Fragment] subclass.
 * Use the [OrderDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderDetailsFragment : Fragment(), MainNavigationFragment {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var cartProductFragmentButtonClick: ButtonClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonClickListener) {
            cartProductFragmentButtonClick = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement listener");
        }
    }

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

        binding.btnOrderNow.setOnClickListener {
            cartProductFragmentButtonClick.confirmButtonClick()
        }

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.cartProductsCount.observe(viewLifecycleOwner, Observer {
            txt_no_of_items.text = it.toString()
        })

        viewModel.cartTotalAmount.observe(viewLifecycleOwner, Observer {
            txt_total_amount.text = it.toString()
        })

        viewModel.cartProductsCount
        viewModel.cartTotalAmount
    }

    companion object {

        fun newInstance() =
                OrderDetailsFragment()
    }
}