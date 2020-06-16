package com.einfoplanet.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.einfoplanet.demo.R
import com.einfoplanet.demo.databinding.ItemRestaurantBinding
import com.einfoplanet.demo.listeners.RestaurantClickedEventListener
import com.einfoplanet.demo.model.Restaurants

/**
 * Adapter to load the Grid of restaurants data in RestaurantFragment.
 */
class RestaurantGridAdapter constructor(
        private val restaurantClickEventListener: RestaurantClickedEventListener,
        private var nearbyRestaurants: List<Restaurants.NearbyRestaurant>
) : RecyclerView.Adapter<RestaurantGridAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemRestaurantBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_restaurant, parent, false
        )
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nearbyRestaurants.size
    }

    fun setList(nearbyRestaurants: List<Restaurants.NearbyRestaurant>) {
        this.nearbyRestaurants = nearbyRestaurants
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(restaurantData = nearbyRestaurants[position].restaurant)
    }

    inner class CustomViewHolder(itemView: ItemRestaurantBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        private var binding: ItemRestaurantBinding? = null

        fun bind(restaurantData: Restaurants.NearbyRestaurant.Restaurant) {
            if (binding == null)
                binding = DataBindingUtil.bind(itemView)

            binding!!.restaurantClickEventListener = restaurantClickEventListener
            binding!!.viewModel = restaurantData
            binding!!.likeDislike.tag = restaurantData.id

            binding!!.likeDislike.setOnClickListener {
                //Logic to handle checking and unchecking of start
                val resId = it.tag
                val index = nearbyRestaurants.indexOfFirst {
                    it.restaurant.id == resId
                }
                val restaurantObj = nearbyRestaurants[index]
                nearbyRestaurants[index].restaurant.isLike = !restaurantObj.restaurant.isLike
                notifyItemChanged(index)
            }
        }
    }
}
