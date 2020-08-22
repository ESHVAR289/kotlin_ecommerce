package com.einfoplanet.demo.ui.life_cycle_test

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast

class TimerToast(application: Application) {

    private val countDownTimer = object : CountDownTimer(60000, 3000) {
        override fun onFinish() {
            Toast.makeText(application, "Timer finished", Toast.LENGTH_SHORT).show()
        }

        override fun onTick(millisUntilFinished: Long) {
            Toast.makeText(application, "Tick : $millisUntilFinished", Toast.LENGTH_SHORT).show()
        }

    }

    fun startCountDownTimer() {
        countDownTimer.start()
    }

    fun cancelCountDownTimer() {
        countDownTimer.cancel()
    }
}