package com.davidturkalj.pcbuildlogger.ui.viewmodels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidturkalj.pcbuildlogger.PCBuildLogger
import com.davidturkalj.pcbuildlogger.data.repository.Repository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository) : ViewModel() {

    private val TAG = "SignInViewModel"

    private var _isUserRegisteredSuccessfully: MutableLiveData<Boolean> = MutableLiveData(false)
    var isUserRegisteredSuccessfully: LiveData<Boolean> = _isUserRegisteredSuccessfully
    private var _isPasswordChangeRequested: MutableLiveData<Boolean> = MutableLiveData(false)
    var isPasswordChangeRequested: LiveData<Boolean> = _isPasswordChangeRequested
    private var _isUserSignedIn: MutableLiveData<Boolean?> = MutableLiveData(null)
    var isUserSignedIn: LiveData<Boolean?> = _isUserSignedIn
    private var _isSigningInSuccessful: MutableLiveData<Boolean> = MutableLiveData(false)
    var isSigningInSuccessful: LiveData<Boolean> = _isSigningInSuccessful

    fun register(email: String, password: String){
        viewModelScope.launch {
            if(email != "" && password != ""){
                _isUserRegisteredSuccessfully.postValue(repository.register(email,password))
            } else {
                Toast.makeText(PCBuildLogger.ApplicationContext,"Error!.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signIn(email: String, password: String){
        viewModelScope.launch {
            _isSigningInSuccessful.postValue(repository.signIn(email,password))
        }
    }

    fun checkIfSignedIn() {
        viewModelScope.launch {
            _isUserSignedIn.postValue(repository.checkIfSignedIn())
        }
    }

    fun getCurrentUser(): FirebaseUser {
        return repository.getUser()
    }

}