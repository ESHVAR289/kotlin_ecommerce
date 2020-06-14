package com.einfoplanet.demo.util

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Allows calls like
 * `viewGroup.inflate(R.layout.foo)`
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

/**
 * Allows calls like
 *
 * `supportFragmentManager.inTransaction{ add(...) }`
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

/**
 * For Activities, allows declarations like
 * ```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
) = ViewModelProviders.of(this).get(VM::class.java)


/**
 * For Fragments, allows declarations like
 *```
 * val myViewModel = viewModelProvider(myViewModelFactory)
 * ```
 */
inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
) = ViewModelProviders.of(this).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Activity.
 */
//inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
//        provider: ViewModelProvider.Factory
//) =
//        ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider() =
        ViewModelProviders.of(requireActivity()).get(VM::class.java)

/**
 * Like [Fragment.viewModelProvider] for Fragments that want a [ViewModel] scoped to the Fragment.
 */
inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(parentFragment!!, provider).get(VM::class.java)
