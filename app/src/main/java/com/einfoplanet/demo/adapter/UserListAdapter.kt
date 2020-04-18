package com.einfoplanet.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.einfoplanet.demo.R
import com.einfoplanet.demo.databinding.ItemUserRowBindingImpl
import com.einfoplanet.demo.listeners.AcceptDeclineButtonClickListener
import com.einfoplanet.demo.model.User

class UserListAdapter constructor(private val btnClickListener: AcceptDeclineButtonClickListener) :
        PagedListAdapter<User, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserRowBindingImpl>(
                LayoutInflater.from(parent.context),
                R.layout.item_user_row, parent, false
        )
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userItem = getItem(position)
        if (userItem != null) {
            (holder as CustomViewHolder).bind(userItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                    oldItem == newItem
        }
    }

    inner class CustomViewHolder(itemView: ItemUserRowBindingImpl) :
            RecyclerView.ViewHolder(itemView.root) {
        private var binding: ItemUserRowBindingImpl? = null

        fun bind(userData: User) {
            if (binding == null)
                binding = DataBindingUtil.bind(itemView)

            binding!!.btnClickListener = btnClickListener
            binding!!.viewModel = userData
        }
    }
}