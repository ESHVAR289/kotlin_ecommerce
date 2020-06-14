package com.einfoplanet.demo.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.einfoplanet.demo.R
import com.einfoplanet.demo.ShoppingSampleApp
import com.einfoplanet.demo.adapter.BooksListAdapter
import com.einfoplanet.demo.databinding.FragmentBooksBinding
import com.einfoplanet.demo.listeners.CoverImageClickListener
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

        booksListAdapter = BooksListAdapter(emptyList(), object : CoverImageClickListener {
            override fun coverImageClick(bookId: String) {
            }

        })

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
        binding.btnAddBook.setOnClickListener {

        }
    }



    override fun onResume() {
        super.onResume()
        booksViewModel.getBooks()
    }

    override fun onAttach(context: Context) {
        ShoppingSampleApp.instance.getApplicationComponent().plusFragmentComponent().inject(this)
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

    private fun showErrorMessage(errorCode: ErrorCode?, message: String?) {
        when (errorCode) {
            ErrorCode.NO_DATA -> activity!!.toast(getString(R.string.error_no_data))
            ErrorCode.NETWORK_ERROR -> activity!!.toast(getString(R.string.error_network, message))
            ErrorCode.UNKNOWN_ERROR -> activity!!.toast(getString(R.string.error_unknown, message))
        }
    }

}