package com.davidturkalj.pcbuildlogger.ui.viewmodels

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidturkalj.pcbuildlogger.PCBuildLogger
import com.davidturkalj.pcbuildlogger.data.repository.Repository
import com.google.firebase.auth.FirebaseUser
import java.io.File
import java.io.FileOutputStream

class TabsViewModel(private val repository: Repository) : ViewModel() {

    private val TAG = "TabsViewModel"

    private var _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    var user: LiveData<FirebaseUser> = _user

    fun updateUser() {
        _user.postValue(repository.getUser())
    }

    fun signOut(){
        repository.signOut()
    }

    suspend fun photo(data: Intent?) {
        val name = System.currentTimeMillis().toString() + ".jpg"
        val file = createFile(System.currentTimeMillis().toString())
        val out = FileOutputStream(file)
        (data?.extras?.get("data") as Bitmap).compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        repository.uploadImage(Uri.fromFile(file), name)
    }

    suspend fun image(imageUri: Uri) {
        val name = System.currentTimeMillis().toString() + ".jpg"
        repository.uploadImage(imageUri, name)
    }

    private fun createFile(name: String): File {
        val storageDir = PCBuildLogger.ApplicationContext.getExternalFilesDir(null)
        val dir = File(storageDir?.absolutePath + "/images")
        if(!dir.exists()){
            dir.mkdir()
        }
        val photo = File(dir,"$name.jpg")
        try {
            if (!photo.createNewFile()) {
                Log.d(TAG, "This file already exist: " + photo.absolutePath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val imagePath = photo.absolutePath
        Log.d(TAG, imagePath)
        return photo
    }


}