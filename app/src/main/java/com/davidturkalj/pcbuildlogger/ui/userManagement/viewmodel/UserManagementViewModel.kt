package com.davidturkalj.pcbuildlogger.ui.userManagement.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.utilities.ResourceHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserManagementViewModel() : ViewModel() {

    private val helper = ResourceHelper()

    var title = MutableLiveData<String>()
    var newUser = MutableLiveData<String>()
    var register = MutableLiveData<String>()
    var userAction = MutableLiveData<String>()
    var firebaseUser = MutableLiveData<FirebaseUser>()
    var postText = MutableLiveData<String>()

    fun switchType() {
        if (title.value == helper.getString(R.string.user_login)) {
            title.postValue(helper.getString(R.string.user_register))
            newUser.postValue(helper.getString(R.string.existing_user))
            register.postValue(helper.getString(R.string.user_login))
            userAction.postValue(helper.getString(R.string.user_register))
        } else {
            title.postValue(helper.getString(R.string.user_login))
            newUser.postValue(helper.getString(R.string.new_user))
            register.postValue(helper.getString(R.string.user_register))
            userAction.postValue(helper.getString(R.string.user_login))
        }
    }

    fun registerUser(email: String, password: String) {
        when {
            validate(email, password) -> {
                postText.postValue("Email or password missing!")
            }
            else -> {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseUser.postValue(task.result!!.user!!)
                            postText.postValue("Registered successfully!")
                        } else {
                            postText.postValue(task.exception!!.message.toString())
                        }
                    }
            }
        }
    }

    fun loginUser(email: String, password: String) {
        when {
            validate(email, password) -> {
                postText.postValue("Email or password missing!")
            }
            else -> {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseUser.postValue(task.result!!.user!!)
                            postText.postValue("Logged in successfully!")
                        } else {
                            postText.postValue(task.exception!!.message.toString())
                        }
                    }
            }
        }
    }

    private fun validate(email: String, password: String): Boolean {
        return when {
            checkEmail(email) -> {
                true
            }
            checkPassword(password) -> {
                true
            }
            else -> false
        }
    }

    private fun checkEmail(email: String): Boolean {
        return TextUtils.isEmpty(email)
    }

    private fun checkPassword(password: String): Boolean {
        return TextUtils.isEmpty(password)
    }
}