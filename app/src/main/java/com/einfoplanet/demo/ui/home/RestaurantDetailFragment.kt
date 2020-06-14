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

    companion object {

        fun newInstance(restaurantId: String) =
                RestaurantDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(SELECTED_RESTAURANT_ID, restaurantId)
                    }
                }
    }
}