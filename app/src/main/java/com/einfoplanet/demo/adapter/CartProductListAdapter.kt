package com.einfoplanet.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.einfoplanet.demo.R
import com.einfoplanet.demo.databinding.CartItemLayoutBinding
import com.einfoplanet.demo.db.CartProduct
import com.einfoplanet.demo.listeners.RemoveCartProductClickEventListener

/**
 * Adapter used to load the photos of selected restaurant.
 */
class CartProductListAdapter constructor(
        private val removeCartProductClickEventListener: RemoveCartProductClickEventListener,
        private var cartProductsList: List<CartProduct>) : RecyclerView.Adapter<CartProductListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<CartItemLayoutBinding>(
                LayoutInflater.from(parent.context),
                R.layout.cart_item_layout, parent, false
        )
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartProductsList.size
    }

    fun setList(cartProductsList: List<CartProduct>) {
        this.cartProductsList = cartProductsList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(cartProduct = cartProductsList[position])
    }

    inner class CustomViewHolder(itemView: CartItemLayoutBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        private var binding: CartItemLayoutBinding? = null

        fun bind(cartProduct: CartProduct) {
            if (binding == null)
                binding = DataBindingUtil.bind(itemView)

            binding!!.removeCartProductClickEventListener = removeCartProductClickEventListener
            binding!!.viewModel = cartProduct
        }
    }
}
