package com.einfoplanet.demo.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.einfoplanet.demo.R
import com.einfoplanet.demo.UserSampleApp
import com.einfoplanet.demo.adapter.AcceptedDeclinedUserListAdapter
import com.einfoplanet.demo.adapter.UserListAdapter
import com.einfoplanet.demo.databinding.UsersListFragmentBinding
import com.einfoplanet.demo.repository.ErrorCode
import com.einfoplanet.demo.repository.Status
import com.einfoplanet.demo.listeners.PageChangeListener
import com.einfoplanet.demo.ui.home.UsersFragment.Companion.ACCEPTED_USERS
import com.einfoplanet.demo.ui.home.UsersFragment.Companion.DECLINED_USERS
import com.einfoplanet.demo.ui.home.UsersFragment.Companion.USERS
import com.einfoplanet.demo.ui.map.MapsActivity
import com.einfoplanet.demo.ui.map.MapsActivity.Companion.MAPS_DATA
import com.einfoplanet.demo.ui.map.MapsActivity.Companion.USER_ADDRESS
import com.einfoplanet.demo.ui.map.MapsActivity.Companion.USER_LAT
import com.einfoplanet.demo.ui.map.MapsActivity.Companion.USER_LNG
import com.einfoplanet.demo.util.toast
import com.einfoplanet.demo.util.viewModelProvider
import kotlinx.android.synthetic.main.users_list_fragment.*
import timber.log.Timber
import javax.inject.Inject

class UsersListFragment : Fragment(), PageChangeListener {
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var acceptedDeclinedAdapter: AcceptedDeclinedUserListAdapter
    @Inject
    lateinit var userListViewModelFactory: UserListViewModelFactory

    companion object {
        private val TAG = UsersListFragment::class.java.simpleName
        private const val ARG_SELECTED_FRAGMENT = "arg.SELECTED_FRAGMENT"

        fun newInstance(selectedFragment: String): UsersListFragment {
            val args = Bundle().apply {
                putString(ARG_SELECTED_FRAGMENT, selectedFragment)
            }
            return UsersListFragment().apply { arguments = args }
        }
    }

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var binding: UsersListFragmentBinding
    private var selectedFragment: String = ""

    override fun onAttach(context: Context) {
        UserSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        userListViewModel = viewModelProvider(userListViewModelFactory)

        binding = UsersListFragmentBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@UsersListFragment)
            viewModel = this@UsersListFragment.userListViewModel
        }
        userListAdapter = UserListAdapter(userListViewModel)
        acceptedDeclinedAdapter = AcceptedDeclinedUserListAdapter(userListViewModel, emptyList())
        binding.recyclerview.layoutManager = GridLayoutManager(context, 2)
        binding.recyclerview.apply {
            adapter = userListAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        binding.rlAcceptedDeclined.layoutManager = GridLayoutManager(context, 2)
        binding.rlAcceptedDeclined.apply {
            adapter = acceptedDeclinedAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }
        val bundle: Bundle = arguments as Bundle
        bundle.let {
            selectedFragment = it.getString(ARG_SELECTED_FRAGMENT)!!
            Timber.d(selectedFragment)
        }

        initViewModel()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        loadDataFrommDB(selectedFragment)
    }

    private fun initViewModel() {
        userListViewModel.users.observe(viewLifecycleOwner, Observer {
            if (selectedFragment == USERS && it.isNotEmpty()) {
                userListAdapter.submitList(it)
                binding.empty.visibility = View.GONE
            }
        })

        userListViewModel.usersLiveDataFromDB.observe(viewLifecycleOwner, Observer {
            if (selectedFragment != USERS && it.isNotEmpty()) {
                binding.rlAcceptedDeclined.visibility = View.VISIBLE
                binding.empty.visibility = View.GONE
                binding.rlAcceptedDeclined.invalidate()
                acceptedDeclinedAdapter.setList(it)
            } else {
                binding.rlAcceptedDeclined.visibility = View.GONE
                binding.empty.visibility = View.VISIBLE
                if (selectedFragment == ACCEPTED_USERS) {
                    binding.empty.text = getString(R.string.label_no_accepted_users)
                } else if (selectedFragment == DECLINED_USERS) {
                    binding.empty.text = getString(R.string.label_no_declined_users)
                }

            }
        })

        userListViewModel.clickedUser.observe(viewLifecycleOwner, Observer {
            val intent = Intent(activity, MapsActivity::class.java)
            val bundle = Bundle()
            val userAddress = "${it.city}, ${it.state}, ${it.country}"

            bundle.putString(USER_ADDRESS, userAddress)
            bundle.putString(USER_LAT, it.latitude)
            bundle.putString(USER_LNG, it.longitude)
            intent.putExtra(MAPS_DATA, bundle)
            if (it.latitude.isNotEmpty() && it.longitude.isNotEmpty()) {
                startActivity(intent)
            }else{
                activity!!.toast(getString(R.string.error_no_data))
            }
        })

        userListViewModel.loadingStatus.observe(viewLifecycleOwner, Observer { loadingStatus ->
            when (loadingStatus?.status) {

                Status.LOADING -> binding.loadingProgress.visibility = View.VISIBLE

                Status.SUCCESS -> {
                    binding.loadingProgress.visibility = View.INVISIBLE
                    toggleRefreshing(false)
                }

                Status.ERROR -> {
                    binding.loadingProgress.visibility = View.INVISIBLE
                    toggleRefreshing(false)
                    showErrorMessage(loadingStatus.errorCode, loadingStatus.message)
                }
            }

        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            userListViewModel.refresh()
        }
    }

    private fun loadDataFrommDB(selectedFragment: String) {
        when (selectedFragment) {
            ACCEPTED_USERS -> userListViewModel.getAcceptedDeclinedUsers(1)
            DECLINED_USERS -> userListViewModel.getAcceptedDeclinedUsers(2)
        }

    }

    override fun refreshDb(selectedFragment: String) {
        loadDataFrommDB(selectedFragment)
    }

    private fun toggleRefreshing(refreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = refreshing
    }

    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> activity!!.toast(getString(R.string.error_no_data))
            ErrorCode.NETWORK_ERROR -> activity!!.toast(getString(R.string.error_network, message))
            ErrorCode.UNKNOWN_ERROR -> activity!!.toast(getString(R.string.error_unknown, message))
        }
    }
}
