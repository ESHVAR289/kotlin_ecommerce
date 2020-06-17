package com.einfoplanet.demo.ui.welcome

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.einfoplanet.demo.R
import com.einfoplanet.demo.ui.home.RestaurantsActivity
import com.einfoplanet.demo.ui.login.LoginActivity
import com.einfoplanet.demo.util.SharedPrefUtil
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    val layouts: IntArray = intArrayOf(
            R.layout.welcome_slide1,
            R.layout.welcome_slide2,
            R.layout.welcome_slide3,
            R.layout.welcome_slide4)
    private val viewPager: ViewPager by lazy { view_pager }
    private val sharedPref: SharedPrefUtil by lazy { SharedPrefUtil(this) }

    //  viewpager change listener
    private var viewPagerPageChangeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size - 1) { // last page. make button text to GOT IT
                btn_next.text = getString(R.string.start)
                btn_skip.visibility = View.GONE
            } else { // still pages are left
                btn_next.text = getString(R.string.next)
                btn_skip.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!sharedPref.isFirstTimeLaunch()) {
            launchHomeScreen()
            finish()
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        setContentView(R.layout.activity_welcome)

        //adding bottom dots
        addBottomDots(0)

        //making notification bar transparent
        changeStatusBarColor()
        viewPager.adapter = CustomViewPager()
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)

        btn_skip.setOnClickListener {
            launchHomeScreen()
        }

        btn_next.setOnClickListener {
            //checking for the last page
            //If last page home screen will be launched
            val currentPage = viewPager.currentItem + 1
            if (currentPage < layouts.size) {
                viewPager.currentItem = currentPage
            } else {
                launchHomeScreen()
            }
        }


    }

    private fun launchHomeScreen() {
        sharedPref.setFirstTimeLaunch(false)
        if (!sharedPref.isLoggedIn()) {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        } else {
            startActivity(Intent(this@WelcomeActivity, RestaurantsActivity::class.java))
        }
        finish()
    }

    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts.size)
        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)
        layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots[i])
        }
        if (dots.isNotEmpty()) dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    inner class CustomViewPager : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View = LayoutInflater.from(container.context).inflate(layouts[position], container, false)
            container.addView(view)
            return view
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view: View = `object` as View
            container.removeView(view)
        }

    }

}
