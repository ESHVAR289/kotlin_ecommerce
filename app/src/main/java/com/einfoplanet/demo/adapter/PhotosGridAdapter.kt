package com.einfoplanet.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.einfoplanet.demo.R
import com.einfoplanet.demo.databinding.ItemPhotoBinding
import com.einfoplanet.demo.model.Restaurant

/**
 * Adapter used to load the photos of selected restaurant.
 */
class PhotosGridAdapter constructor(
        private var photosList: List<Restaurant.Photo>) : RecyclerView.Adapter<PhotosGridAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = DataBindingUtil.inflate<ItemPhotoBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_photo, parent, false
        )
        return CustomViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    fun setList(photosList: List<Restaurant.Photo>) {
        this.photosList = photosList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(photoUri = photosList[position].photo.thumbUrl)
    }

    inner class CustomViewHolder(itemView: ItemPhotoBinding) :
            RecyclerView.ViewHolder(itemView.root) {
        private var binding: ItemPhotoBinding? = null

        fun bind(photoUri: String) {
            if (binding == null)
                binding = DataBindingUtil.bind(itemView)
            binding!!.imgUrl = photoUri
        }
    }
}
