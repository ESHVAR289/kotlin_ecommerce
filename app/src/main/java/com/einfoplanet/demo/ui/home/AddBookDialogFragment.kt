package com.einfoplanet.demo.ui.home

import android.R
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.einfoplanet.demo.BookSampleApp
import com.einfoplanet.demo.databinding.AddBookDialogFragmentBinding
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.util.viewModelProvider
import javax.inject.Inject
import kotlin.random.Random


class AddBookDialogFragment : DialogFragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = AddBookDialogFragment()
    }

    @Inject
    lateinit var userListViewModelFactory: BookListViewModelFactory
    private lateinit var booksViewModel: BookListViewModel
    private lateinit var binding: AddBookDialogFragmentBinding
    private var isImageSelected: Boolean = false
    private var isDateSelected: Boolean = false
    private var isAuthorSelected: Boolean = false
    private var strSelectedAuthor: String = ""
    private var strDateSelected: String = ""

    override fun onAttach(context: Context) {
        BookSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        booksViewModel = viewModelProvider(userListViewModelFactory)

        binding = AddBookDialogFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@AddBookDialogFragment
            spinnerAuthorName.onItemSelectedListener = this@AddBookDialogFragment
        }


        binding.btnSelectDate.setOnClickListener {
            val newFragment = DatePickerFragment(DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                isDateSelected = true
                strDateSelected = "$dayOfMonth/$month/$year"
                binding.txtDoi.text = "Date of Issue : $strDateSelected"
            })
            newFragment.show(parentFragmentManager, "datePicker")
        }

        binding.btnChooseCover.setOnClickListener {

        }

        binding.btnSubmit.setOnClickListener {
            insertData()
        }


        setAuthorsData()
        return binding.root
    }

    private fun setAuthorsData() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(context!!, com.einfoplanet.demo.R.array.authors_array, R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerAuthorName.setSelection(0)
            binding.spinnerAuthorName.adapter = adapter
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }

    private fun insertData() {
        if (isValidData()) {
            booksViewModel.insertBookData(getBookData())
            Toast.makeText(context!!, "Data inserted !!", Toast.LENGTH_SHORT).show()
            dismiss()
        } else {
            Toast.makeText(context!!, "Invalid data !!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isValidData(): Boolean {
        return !(binding.etBookName.toString().isEmpty() ||
                binding.etPrice.toString().isEmpty() ||
                !isDateSelected ||
                !isAuthorSelected)
    }

    private fun getBookData(): BookDetail {
        return BookDetail(bookName = binding.etBookName.text.toString(),
                authorName = strSelectedAuthor,
                dateOfIssue = strDateSelected,
                price = binding.etPrice.text.toString(),
                id = rand(1, 1000))
    }

    override fun onResume() {
        // Get existing layout params for the window
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        // Call super onResume after sizing
        super.onResume()
    }

    private fun rand(start: Int, end: Int): String {
        require(start <= end) { "Illegal Argument" }
        val rand = Random(System.nanoTime())
        return (start..end).random(rand).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        isAuthorSelected = parent?.getItemAtPosition(position).toString() != "Select Author"
        strSelectedAuthor = parent?.getItemAtPosition(position).toString()
    }
}

