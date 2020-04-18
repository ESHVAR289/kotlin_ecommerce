package com.einfoplanet.demo.adapter

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.einfoplanet.demo.R
import com.einfoplanet.demo.repository.LoadingStatus
import com.einfoplanet.demo.repository.Status

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imagePath: String?) {

    imagePath?.let {
        Glide.with(imageView.context)
                .load(imagePath)
                .into(imageView)
    }
}

@BindingAdapter("changeAcceptButtonState")
fun changeAcceptButtonState(btn: AppCompatButton, isFlag: Int) {
    when (isFlag) {
        0 -> {
            btn.apply {
                isEnabled = true
                setBackgroundColor(ContextCompat.getColor(btn.context, android.R.color.holo_green_dark))
            }
        }
        1 -> {
            btn.apply {
                visibility = View.VISIBLE
                isEnabled = false
                isClickable = false
                text = context.getString(R.string.label_member_accepted)
                setBackgroundColor(ContextCompat.getColor(btn.context, android.R.color.holo_green_light))
            }
        }
        2 -> {
            btn.visibility = View.GONE
        }
    }
}

@BindingAdapter("changeNegativeButtonState")
fun changeNegativeButtonState(btn: AppCompatButton, isFlag: Int) {
    when (isFlag) {
        0 -> {
            btn.apply {
                isEnabled = true
                setBackgroundColor(ContextCompat.getColor(btn.context, android.R.color.holo_red_dark))
            }
        }
        1 -> {
            btn.visibility = View.GONE
        }
        2 -> {
            btn.apply {
                visibility = View.VISIBLE
                isEnabled = false
                isClickable = false
                text = context.getString(R.string.label_member_declined)
                setBackgroundColor(ContextCompat.getColor(btn.context, android.R.color.holo_red_light))
            }
        }
    }
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, loadingStatus: LoadingStatus?) {
    loadingStatus?.let {
        when (loadingStatus.status) {
            Status.SUCCESS -> view.visibility = View.GONE
            Status.ERROR -> view.visibility = View.GONE
            Status.LOADING -> view.visibility = View.GONE
        }
    }
}

//When both the data from db & api are empty visible the no data text view
@BindingAdapter(value = ["dbUserListSize", "apiUserListSize"], requireAll = true)
fun goneIfEmptyList(
        view: View,
        dbUserListSize: Int,
        apiUserListSize: Int) {
    view.visibility = if (dbUserListSize > 0 || apiUserListSize > 0) View.GONE else View.VISIBLE
}

@BindingAdapter("pageMargin")
fun pageMargin(viewPager: ViewPager, pageMargin: Float) {
    viewPager.pageMargin = pageMargin.toInt()
}