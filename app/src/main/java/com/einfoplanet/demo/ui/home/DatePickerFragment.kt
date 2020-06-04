package com.einfoplanet.demo.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.einfoplanet.demo.UserSampleApp
import com.einfoplanet.demo.util.viewModelProvider
import java.util.*
import javax.inject.Inject

class DatePickerFragment constructor(val listener: DatePickerDialog.OnDateSetListener) : DialogFragment() {

    @Inject
    lateinit var userListViewModelFactory: UserListViewModelFactory
    private lateinit var booksViewModel: UserListViewModel
    private lateinit var fragmentContext: Context

    companion object {
        fun newInstance() = AddBookDialogFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(context!!, listener , year, month, day)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        booksViewModel = viewModelProvider(userListViewModelFactory)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        fragmentContext = context
        UserSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
    }

}