package com.einfoplanet.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.einfoplanet.demo.R
import com.einfoplanet.demo.adapter.RestaurantGridAdapter
import com.einfoplanet.demo.databinding.FragmentRestaurantsBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.AppConstants.Companion.CURRENT_LATITUDE
import com.einfoplanet.demo.util.AppConstants.Companion.CURRENT_LONGITUDE
import com.einfoplanet.demo.util.NetworkUtils
import com.einfoplanet.demo.util.activityViewModelProvider
import org.jetbrains.anko.runOnUiThread

/**
 * This is the fragment which is used to show the list of restaurants.
 * On restaurant selection we are redirecting user to restaurant detail page.
 */
class RestaurantsFragment : Fragment(), MainNavigationFragment {
    companion object {
        private val TAG = RestaurantsFragment::class.java.simpleName

        fun newInstance(
                lat: Double? = 0.0,
                lon: Double? = 0.0
        ): RestaurantsFragment {
            val args = Bundle().apply {
                putDouble(CURRENT_LATITUDE, lat!!)
                putDouble(CURRENT_LONGITUDE, lon!!)
            }
            return RestaurantsFragment().apply { arguments = args }
        }
    }

    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var binding: FragmentRestaurantsBinding
    private lateinit var restaurantAdapter: RestaurantGridAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = activityViewModelProvider()
        binding = FragmentRestaurantsBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@RestaurantsFragment)
            viewModel = this@RestaurantsFragment.viewModel
        }

        restaurantAdapter = RestaurantGridAdapter(viewModel, emptyList())
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.apply {
            adapter = restaurantAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        arguments?.let {
            val bundle: Bundle = arguments as Bundle
            bundle.let {
                viewModel.currentLatitude = it.getDouble(CURRENT_LATITUDE)
                viewModel.currentLongitude = it.getDouble(CURRENT_LONGITUDE)
            }
        }

        initViewModel()
        viewModel.getNearbyRestaurants(0.0, 0.0)
        return binding.root
    }

    private fun initViewModel() {

        viewModel.restaurantsLiveData.observe(viewLifecycleOwner, Observer {
            restaurantAdapter.setList(it)
        })

        //Show an error message
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let { errorMessage ->
                context!!.runOnUiThread {
                    when (it) {
                        "Something bad happen. Please try again !!" -> binding.txtError.visibility =
                                View.VISIBLE
                        "No Favourites found !!" -> binding.txtError.visibility =
                                View.VISIBLE
                        "No cuisine found !!" -> binding.txtError.visibility =
                                View.VISIBLE
                        else -> binding.txtError.visibility = View.GONE
                    }
                }
            }
        })

        checkNetworkAndLoadData()
    }

    private fun checkNetworkAndLoadData() {
        if (NetworkUtils(context = context!!).hasNetworkConnection()) {
            loadDataFromNetwork()
        } else {
            Toast.makeText(
                    activity,
                    getString(R.string.error_no_internet),
                    Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun loadDataFromNetwork() {
        if (NetworkUtils(context = context!!).hasNetworkConnection()) {

            viewModel.getNearbyRestaurants(
                    viewModel.currentLatitude,
                    viewModel.currentLongitude
            )


        } else Toast.makeText(
                activity,
                getString(R.string.error_no_internet),
                Toast.LENGTH_SHORT
        ).show()
    }

    override fun onBackPressed(): Boolean {
        return true
    }

}