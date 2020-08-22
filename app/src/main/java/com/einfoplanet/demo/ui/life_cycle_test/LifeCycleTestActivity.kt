package com.einfoplanet.demo.ui.life_cycle_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.einfoplanet.demo.R

class LifeCycleTestActivity : AppCompatActivity() {
    private lateinit var timerToast: TimerToast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle_test)
        timerToast = TimerToast(application)
    }
}