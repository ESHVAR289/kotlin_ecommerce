package com.einfoplanet.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.einfoplanet.demo.R
import com.einfoplanet.demo.databinding.ItemUserRowBindingImpl
import com.einfoplanet.demo.listeners.AcceptDeclineButtonClickListener
import com.einfoplanet.demo.model.User


class AcceptedDeclinedUserListAdapter constructor(
        internal val btnClickListener: AcceptDeclineButtonClickListener,
        internal var usersListData: List<User>
) : RecyclerView.Adapter<AcceptedDeclinedUserListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserRowBindingImpl>(
                LayoutInflater.from(parent.context),
                R.layout.item_user_row, parent, false
        )
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return usersListData.size
    }

    fun setList(updatedList: List<User>) {
        usersListData = updatedList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(userData = usersListData.get(position))
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
