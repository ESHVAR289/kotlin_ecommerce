package com.einfoplanet.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.einfoplanet.demo.databinding.FragmentRestaurantDetailBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.ui.login.afterTextChanged
import com.einfoplanet.demo.util.AppConstants.Companion.SELECTED_RESTAURANT_ID
import com.einfoplanet.demo.util.activityViewModelProvider

/**
 * A simple [Fragment] subclass.
 * Use the [RestaurantDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestaurantDetailFragment : Fragment(), MainNavigationFragment {

    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var binding: FragmentRestaurantDetailBinding
    private var quantity: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activityViewModelProvider()
        binding = FragmentRestaurantDetailBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@RestaurantDetailFragment
            viewModel = this@RestaurantDetailFragment.viewModel
        }

        arguments?.let {
            val bundle: Bundle = arguments as Bundle
            bundle.let {
                viewModel.selectedResId = it.getString(SELECTED_RESTAURANT_ID)!!
            }
        }

        binding.txtAddToCart.setOnClickListener {
            //Check before adding product into cart
            viewModel.cartProductsLiveData.value?.forEach {
                if (it.restaurantId == viewModel.singleRestaurantData.value!!.id) {
                    Toast.makeText(requireContext(), "Product already present into your cart.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            viewModel.addProductToCart(viewModel.singleRestaurantData.value!!)
            Toast.makeText(requireContext(), "Product added successfully.", Toast.LENGTH_SHORT).show()
        }

        binding.btnIncrementQuantity.setOnClickListener {
            increment()
        }

        binding.btnDecrementQuantity.setOnClickListener {
            decrement()
        }

        binding.etProductQuantity.afterTextChanged {
            if (binding.etProductQuantity.text.isEmpty()) {
                binding.etProductQuantity.setText("0")
            } else if (binding.etProductQuantity.text.toString().toInt() >= 10) {
                Toast.makeText(requireContext(), "Product Count Must be less than 10", Toast.LENGTH_LONG).show()
            }
        }

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        viewModel.singleRestaurantData.observe(viewLifecycleOwner, Observer {
            binding.restaurantDetail = it
        })


        //Show an error message
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        })

        viewModel.getRestaurantDetail(viewModel.selectedResId)
    }

    private fun increment() {
        if (quantity < 10) {
            quantity++
            binding.etProductQuantity.setText(quantity.toString())
        } else {
            Toast.makeText(requireContext(), "Product Count Must be less than 10", Toast.LENGTH_LONG).show()
        }
    }

    private fun decrement() {
        if (quantity > 1) {
            quantity--
            binding.etProductQuantity.setText(quantity.toString())
        } else
            binding.etProductQuantity.setText("0")
    }

    companion object {

        fun newInstance(restaurantId: String) =
                RestaurantDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(SELECTED_RESTAURANT_ID, restaurantId)
                    }
                }
    }
}