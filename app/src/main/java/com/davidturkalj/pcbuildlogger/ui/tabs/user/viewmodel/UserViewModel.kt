package com.davidturkalj.pcbuildlogger.ui.tabs.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class UserViewModel() : ViewModel() {

    val email = MutableLiveData<String>()
    val userId = MutableLiveData<String>()
    val isLogout = MutableLiveData<Boolean>()

    fun setData(email: String?, userId: String?) {
        this.email.postValue(email)
        this.userId.postValue(userId)
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        isLogout.postValue(true)
    }
}