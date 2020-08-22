package com.einfoplanet.demo.ui.life_cycle_test

import android.app.Application
import android.os.CountDownTimer

class TimerToast(application: Application) {

    private val countDownTimer = object : CountDownTimer(60000, 3000) {
        override fun onFinish() {

        }

        override fun onTick(millisUntilFinished: Long) {

        }

    }
}