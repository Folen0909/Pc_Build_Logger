package com.davidturkalj.pcbuildlogger.utilities

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

object DatabaseHelper {

    private val storage = Firebase.storage

    private val storageReference = storage.reference

    val imagesRef: StorageReference? = storageReference.child("images/")

    private val database =
        Firebase.database("https://pc-build-logger-default-rtdb.europe-west1.firebasedatabase.app/")

    var userId: String = ""

    var buildsReference = database.getReference("$userId/builds")

    var componentsReference = database.getReference("$userId/builds/")

    fun changeUserId(userId: String?) {
        this.userId = userId!!
        buildsReference = database.getReference("${this.userId}/builds")
    }

    fun changeBuild(buildKey: String?) {
        componentsReference = database.getReference("${this.userId}/builds/$buildKey/components")
    }
}