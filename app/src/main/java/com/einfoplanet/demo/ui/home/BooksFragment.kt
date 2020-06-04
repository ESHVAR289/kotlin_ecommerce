package com.einfoplanet.demo.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.einfoplanet.demo.R
import com.einfoplanet.demo.BookSampleApp
import com.einfoplanet.demo.adapter.BooksListAdapter
import com.einfoplanet.demo.databinding.FragmentBooksBinding
import com.einfoplanet.demo.listeners.MainNavigationFragment
import com.einfoplanet.demo.repository.ErrorCode
import com.einfoplanet.demo.util.toast
import com.einfoplanet.demo.util.viewModelProvider
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [BooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BooksFragment : Fragment(), MainNavigationFragment {
    companion object {
        const val DIALOG_FRAGMENT_TAG: String = "DIALOG_FRAGMENT"
    }

    @Inject
    lateinit var bookListViewModelFactory: BookListViewModelFactory
    private lateinit var booksViewModel: BookListViewModel
    private lateinit var binding: FragmentBooksBinding
    private lateinit var booksListAdapter: BooksListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        booksViewModel = viewModelProvider(bookListViewModelFactory)

        binding = FragmentBooksBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@BooksFragment)
            viewModel = this@BooksFragment.booksViewModel
        }




        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        booksListAdapter = BooksListAdapter(emptyList())
        binding.rlBooks.layoutManager = LinearLayoutManager(context)
        binding.rlBooks.apply {
            adapter = booksListAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<AppCompatButton>(R.id.btn_add_book).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Log.e("fragment","btn clicked")
                val fm: FragmentManager = parentFragmentManager
                val editNameDialogFragment: AddBookDialogFragment = AddBookDialogFragment.newInstance()
                editNameDialogFragment.show(fm, "fragment_edit_name")
            }

        })
    }



    override fun onResume() {
        super.onResume()
        booksViewModel.getBooks()
    }

    override fun onAttach(context: Context) {
        BookSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
        super.onAttach(context)
    }

    fun initViewModel() {

        booksViewModel.books.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                binding.rlBooks.visibility = View.VISIBLE
                binding.empty.visibility = View.GONE
                binding.rlBooks.invalidate()
                booksListAdapter.setList(it)
            } else {
                binding.rlBooks.visibility = View.GONE
                binding.empty.visibility = View.VISIBLE
                binding.empty.text = getString(R.string.label_no_books_in_db)

            }
        })

    }

    /**
     * method which shows the dialog for the error.
     */
    fun showDialog(dialogFragment: DialogFragment) {

        parentFragmentManager.let {
            var ft = it.beginTransaction()
            val prev = it.findFragmentByTag(DIALOG_FRAGMENT_TAG)

            prev?.let { f ->
                ft.remove(prev)
                ft.commitAllowingStateLoss()
                ft = it.beginTransaction()
            }

            // Following is not crashing, still a precaution
            try {
                ft.remove(dialogFragment)
                ft.commitAllowingStateLoss()
                ft = it.beginTransaction()
            } catch (e: Exception) {
            }

            ft.apply {
                // Create and show the dialog.
                dialogFragment.showsDialog = true
                addToBackStack(null)
                add(dialogFragment, DIALOG_FRAGMENT_TAG)
                commitAllowingStateLoss()
                // dialogFragment.show(ft, AppConstants.KEY_DIALOG)
            }
        }
    }

    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> activity!!.toast(getString(R.string.error_no_data))
            ErrorCode.NETWORK_ERROR -> activity!!.toast(getString(R.string.error_network, message))
            ErrorCode.UNKNOWN_ERROR -> activity!!.toast(getString(R.string.error_unknown, message))
        }
    }
}