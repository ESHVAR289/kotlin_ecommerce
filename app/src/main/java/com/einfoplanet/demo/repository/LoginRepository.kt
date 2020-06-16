package com.einfoplanet.demo.repository

import androidx.lifecycle.MutableLiveData
import com.einfoplanet.demo.model.LoggedInUser
import com.einfoplanet.demo.ui.login.LoginResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String, _loginResult: MutableLiveData<LoginResult>) {
        // handle login
//        val result = dataSource.login(username, password)
        val result = dataSource.signIn(username, password, _loginResult)

//        if (result is Result.Success) {
//            setLoggedInUser(result.data)
//        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}