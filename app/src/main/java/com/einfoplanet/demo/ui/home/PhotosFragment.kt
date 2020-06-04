package com.einfoplanet.demo.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.einfoplanet.demo.BookSampleApp
import com.einfoplanet.demo.adapter.PhotosGridAdapter
import com.einfoplanet.demo.databinding.FragmentPhotosBinding
import com.einfoplanet.demo.util.viewModelProvider
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [PhotosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotosFragment : DialogFragment() {

    @Inject
    lateinit var bookListViewModelFactory: BookListViewModelFactory
    private lateinit var booksViewModel: BookListViewModel
    private var bookId: String = ""
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var photosGridAdapter: PhotosGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookId = it.getString(Companion.SELECTED_COVER_BOOK_ID).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        booksViewModel = viewModelProvider(bookListViewModelFactory)
        // Inflate the layout for this fragment
        binding = FragmentPhotosBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@PhotosFragment
        }

        photosGridAdapter = PhotosGridAdapter(emptyList())
        binding.rlImages.layoutManager = GridLayoutManager(context, 3)
        binding.rlImages.apply {
            adapter = photosGridAdapter
            (itemAnimator as DefaultItemAnimator).run {
                supportsChangeAnimations = false
                addDuration = 160L
                moveDuration = 160L
                changeDuration = 160L
                removeDuration = 120L
            }
        }

        initViewModel()
        return binding.root
    }

    private fun initViewModel() {
        booksViewModel.getImagesData(bookId)
        booksViewModel.imagesLiveData.observe(viewLifecycleOwner, Observer {
            photosGridAdapter.setList(it)
        })
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss()
    }

    override fun onAttach(context: Context) {
        BookSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
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

    companion object {
        @JvmStatic
        fun newInstance(bookId: String) =
                PhotosFragment().apply {
                    arguments = Bundle().apply {
                        putString(Companion.SELECTED_COVER_BOOK_ID, bookId)
                    }
                }

        private const val SELECTED_COVER_BOOK_ID = "BOOK_ID"
    }
}