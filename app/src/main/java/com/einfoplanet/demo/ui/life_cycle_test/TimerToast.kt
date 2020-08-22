package com.einfoplanet.demo.ui.life_cycle_test

import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class TimerToast(application: Application, lifecycleOwner: LifecycleOwner) : LifecycleObserver {
    private var started = false
    private val lifecycle = lifecycleOwner.lifecycle

    private val countDownTimer = object : CountDownTimer(60000, 3000) {
        override fun onFinish() {
            Toast.makeText(application, "Timer finished", Toast.LENGTH_SHORT).show()
        }

        override fun onTick(millisUntilFinished: Long) {
            Toast.makeText(application, "Tick : $millisUntilFinished", Toast.LENGTH_SHORT).show()
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun startCountDownTimer() {
        if (!started) {
            countDownTimer.start()
            started = true
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCountDownTimer() {
        countDownTimer.cancel()
    }
}