package com.einfoplanet.demo.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.einfoplanet.demo.R
import com.einfoplanet.demo.UserSampleApp
import com.einfoplanet.demo.databinding.UsersFragmentBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.listeners.PageChangeListener
import com.einfoplanet.demo.util.viewModelProvider
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class UsersFragment : Fragment(), MainNavigationFragment {
    companion object {
        const val USERS: String = "USERS"
        const val ACCEPTED_USERS: String = "ACCEPTED"
        const val DECLINED_USERS: String = "DECLINED"
        private val viewPagerDataItems: List<String> = listOf(USERS, DECLINED_USERS, ACCEPTED_USERS)
        private val COUNT: Int = viewPagerDataItems.size
    }

    @Inject
    lateinit var userListViewModelFactory: UserListViewModelFactory
    private lateinit var usersViewModel: UserListViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var refreshDb: PageChangeListener
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        usersViewModel = viewModelProvider(userListViewModelFactory) //Extention function to get the parent ViewModel
        val binding = UsersFragmentBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@UsersFragment)
            viewModel = this@UsersFragment.usersViewModel
        }

        coordinatorLayout = binding.coordinatorLayout
        viewPager = binding.viewpager
        viewPager.adapter = ViewPageAdapter(childFragmentManager)
        return binding.root
    }

    override fun onAttach(context: Context) {
        UserSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPager.offscreenPageLimit = 2
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                refreshDb.refreshDb(viewPagerDataItems.get(position))
            }
        })

    }

    /**
     * Adapter that builds a page
     */
    inner class ViewPageAdapter(fm: FragmentManager) :
            FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            val fragment = UsersListFragment.newInstance(viewPagerDataItems[position])
            refreshDb = fragment
            return fragment
        }

        override fun getCount() = COUNT

        override fun getPageTitle(position: Int): CharSequence? = viewPagerDataItems[position]

    }
}
