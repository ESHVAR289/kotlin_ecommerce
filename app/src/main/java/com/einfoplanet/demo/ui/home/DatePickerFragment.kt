package com.einfoplanet.demo.ui.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.einfoplanet.demo.BookSampleApp
import java.util.*

class DatePickerFragment constructor(val listener: DatePickerDialog.OnDateSetListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(context!!, listener , year, month, day)
    }

    override fun onAttach(context: Context) {
        BookSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
    }

}