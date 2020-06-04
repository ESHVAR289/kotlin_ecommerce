package com.einfoplanet.demo.ui.home

import android.R
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.einfoplanet.demo.BookSampleApp
import com.einfoplanet.demo.adapter.PhotosGridAdapter
import com.einfoplanet.demo.databinding.AddBookDialogFragmentBinding
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.db.BookImagesData
import com.einfoplanet.demo.util.viewModelProvider
import com.fxn.pix.Options
import com.fxn.pix.Pix
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
    private var bookId: String = rand(1, 1000)
    private lateinit var photosGridAdapter: PhotosGridAdapter
    var returnValue: List<String> = emptyList()
    var imagesData: List<BookImagesData> = mutableListOf()

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
            val options: Options = Options.init()
                    .setExcludeVideos(true)
                    .setRequestCode(100) //Request code for activity results
                    .setCount(5) //Number of images to restict selection count
                    .setFrontfacing(false) //Front Facing camera on start
                    .setVideoDurationLimitinSeconds(30) //Duration for video recording
                    .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientaion

            Pix.start(this, options)
        }

        binding.btnSubmit.setOnClickListener {
            returnValue.let {
                if (returnValue.isNotEmpty()) {
                    insertImageData(imagesData)
                    insertData(returnValue[0])
                } else {
                    Toast.makeText(context!!, "Invalid data !!", Toast.LENGTH_SHORT).show()
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            returnValue = data?.getStringArrayListExtra(Pix.IMAGE_RESULTS)!!.toList()
            returnValue.let {
                imagesData = getImageBookData(returnValue)
                if (imagesData.isNotEmpty() && bookId.isNotEmpty()) {
                    isImageSelected = true
                    showSelectedImages(imagesData)
                } else {
                    Toast.makeText(context!!, "Nothing selected !!", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }

    private fun insertData(coverImagePath: String) {
        if (isValidData()) {
            booksViewModel.insertBookData(getBookData(coverImagePath))
            Toast.makeText(context!!, "Data inserted !!", Toast.LENGTH_SHORT).show()
            dismiss()
        } else {
            Toast.makeText(context!!, "Invalid data !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun insertImageData(imagesData: List<BookImagesData>) {
        booksViewModel.insertBooksImages(imagesData)
    }


    //Check if all the data field are filled
    private fun isValidData(): Boolean {
        return !(binding.etBookName.toString().isEmpty() ||
                binding.etPrice.toString().isEmpty() ||
                !isDateSelected ||
                !isAuthorSelected)
    }

    private fun showSelectedImages(imagesData: List<BookImagesData>) {
        photosGridAdapter = PhotosGridAdapter(imagesData)
        binding.imgNoData.visibility = View.INVISIBLE
        binding.rlGalleryImages.layoutManager = GridLayoutManager(context, 3)
        binding.rlGalleryImages.apply {
            adapter = photosGridAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }
    }

    private fun getBookData(coverImagePath: String): BookDetail {
        return BookDetail(bookName = binding.etBookName.text.toString(),
                authorName = strSelectedAuthor,
                dateOfIssue = strDateSelected,
                price = binding.etPrice.text.toString(),
                id = bookId,
                coverImgPath = coverImagePath)
    }

    private fun getImageBookData(returnValue: List<String>): List<BookImagesData> {
        val imageBookData: ArrayList<BookImagesData> = mutableListOf<BookImagesData>() as ArrayList<BookImagesData>
        returnValue.forEach {
            imageBookData.add(BookImagesData(rand(1, 1000), bookId, it))
        }
        return imageBookData
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

