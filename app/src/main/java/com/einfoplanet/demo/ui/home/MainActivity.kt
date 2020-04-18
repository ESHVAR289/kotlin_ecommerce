package com.einfoplanet.demo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.einfoplanet.demo.R
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.util.inTransaction

class MainActivity : AppCompatActivity() {
    companion object {
        private const val FRAGMENT_ID = R.id.fragment_container
    }

    private lateinit var currentFragment: MainNavigationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(UsersFragment())
    }

    private fun <F> replaceFragment(fragment: F) where F : Fragment, F : MainNavigationFragment {
        supportFragmentManager.inTransaction {
            currentFragment = fragment
            replace(FRAGMENT_ID, fragment)
        }
    }

    override fun onBackPressed() {
        if (!currentFragment.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        currentFragment.onUserInteraction()
    }

}
