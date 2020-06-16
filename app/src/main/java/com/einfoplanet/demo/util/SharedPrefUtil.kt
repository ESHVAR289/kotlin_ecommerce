package com.einfoplanet.demo.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil(context: Context) {
    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor

    companion object {
        private const val IS_FIRST_TIME_LAUNCH = "isFirstTimeLaunch"
        private const val LOGGED_IN_USER_EMAIL_ID = "emailId"
        private const val PREFERENCE_NAME = "sharedPrefFileName"
        private const val IS_LOGIN = "isLogin"

        // Shared pref mode
        private const val PRIVATE_MODE = 0
    }

    init {
        pref = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }

    fun setLoginData(userEmail: String, isLogin: Boolean) {
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.putString(LOGGED_IN_USER_EMAIL_ID, userEmail)
        editor.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }
}