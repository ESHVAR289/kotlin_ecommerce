package com.einfoplanet.demo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.einfoplanet.demo.R
import com.einfoplanet.demo.model.LoggedInUser
import com.einfoplanet.demo.ui.login.LoggedInUserView
import com.einfoplanet.demo.ui.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.io.IOException
import java.util.*


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    companion object {
        private val TAG = LoginDataSource::class.java.simpleName
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), "Jane Doe")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }


    fun signUpWithFirebase(username: String, password: String, _loginResult: MutableLiveData<LoginResult>) {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.e(TAG, "createUserWithEmail:success")
                        val user: FirebaseUser = it.result?.user!!
                        _loginResult.postValue(LoginResult(success = LoggedInUserView(userEmail = user.email!!)))
                    } else {
                        //If sign in fails, display failed message`
                        _loginResult.postValue(LoginResult(error = R.string.login_failed))
                    }
                }
    }

    fun signIn(username: String, password: String, _loginResult: MutableLiveData<LoginResult>) {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.e(TAG, "signInWithEmailAndPassword:success")
                        val user: FirebaseUser = it.result?.user!!
                        _loginResult.postValue(LoginResult(success = LoggedInUserView(userEmail = user.email!!)))
                    } else {
                        signUpWithFirebase(username,password, _loginResult)
                    }
                }
    }


    fun logout() {
        // TODO: revoke authentication
    }
}