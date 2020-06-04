package com.einfoplanet.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.einfoplanet.demo.R
import com.einfoplanet.demo.databinding.ItemBookDetailBinding
import com.einfoplanet.demo.db.BookDetail
import com.einfoplanet.demo.listeners.CoverImageClickListener


class BooksListAdapter constructor(
        private var booksListData: List<BookDetail>,
        private val coverImageClickListener: CoverImageClickListener
) : RecyclerView.Adapter<BooksListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemBookDetailBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_book_detail, parent, false
        )
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return booksListData.size
    }

    fun setList(updatedBooksList: List<BookDetail>) {
        booksListData = updatedBooksList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(bookData = booksListData.get(position))
    }

    inner class CustomViewHolder(itemView: ItemBookDetailBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        private var binding: ItemBookDetailBinding? = null

        fun bind(bookData: BookDetail) {
            if (binding == null)
                binding = DataBindingUtil.bind(itemView)
            binding!!.converImageClickListener = coverImageClickListener
            binding!!.viewModel = bookData
        }
    }
}
