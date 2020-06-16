package com.einfoplanet.demo.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
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

@BindingAdapter("imgUrl")
fun bindImageWithProgress(imageView: ImageView, imagePath: String?) {
    val progressBar = imageView.rootView.findViewById<ProgressBar>(R.id.progress_bar_rl)
    progressBar.visibility = View.VISIBLE
    imagePath?.let {
        Glide.with(imageView.context)
                .load(imagePath)
                .placeholder(R.drawable.drawerback)
                .error(R.drawable.drawerback)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
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

@BindingAdapter("ratingColor")
fun setTextViewTint(textView: TextView, colorValue: String?) {

    textView.background = (textView.background as? GradientDrawable ?: GradientDrawable()).apply {
        setTint(Color.parseColor("#$colorValue"))
    }
}

@BindingAdapter("likeDislike")
fun likeDislike(imageView: ImageView, isLike: Boolean) {
    if (isLike) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.avd_star_event))
    } else {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.drawable.ic_star_border))
    }
}

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    if (show) view.visibility = View.VISIBLE else view.visibility = View.GONE
}