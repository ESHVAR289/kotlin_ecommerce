package com.einfoplanet.demo.ui.cart

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.einfoplanet.demo.adapter.CartProductListAdapter
import com.einfoplanet.demo.databinding.FragmentCartProductsListingBinding
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.listeners.ButtonClickListener
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.activityViewModelProvider


/**
 * A simple [Fragment] subclass.
 * Use the [CartProductsListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartProductsListingFragment : Fragment(), MainNavigationFragment {

    private lateinit var viewModel: CartViewModel
    private lateinit var binding: FragmentCartProductsListingBinding
    private lateinit var cartProductsListAdapter: CartProductListAdapter
    private lateinit var buttonClick: ButtonClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activityViewModelProvider()
        binding = FragmentCartProductsListingBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CartProductsListingFragment
            viewModel = this@CartProductsListingFragment.viewModel
        }

        cartProductsListAdapter = CartProductListAdapter(viewModel, emptyList())
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.apply {
            adapter = cartProductsListAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        binding.btnPlaceOrder.setOnClickListener {
            buttonClick.placeOrderButtonClick()
        }
        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.cartProductsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.recyclerview.visibility = View.VISIBLE
                binding.ivEmptyCart.visibility = View.GONE
                binding.llLayout.visibility = View.VISIBLE
                cartProductsListAdapter.setList(it)
            } else {
                binding.ivEmptyCart.visibility = View.VISIBLE
                binding.recyclerview.visibility = View.GONE
                binding.llLayout.visibility = View.GONE
            }
        })
        viewModel.cartProductsCount.observe(viewLifecycleOwner, Observer {
            Log.e("cart count", "count $it")
        })
        viewModel.productSelectedToRemove.observe(viewLifecycleOwner, Observer {
            showAlertDialog(it)
        })
        viewModel.cartProductsLiveData
        viewModel.cartProductsCount
    }

    fun showAlertDialog(cartProduct: CartProduct) {
        val alertDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("Do you want to remove product from cart?")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            viewModel.removeCartProduct(cartProduct)
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ButtonClickListener) {
            buttonClick = context
        } else {
            throw RuntimeException(context.toString()
                    + " must implement listener");
        }
    }

    companion object {

        fun newInstance() =
                CartProductsListingFragment()
    }
}