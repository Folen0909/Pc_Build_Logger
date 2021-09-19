package com.davidturkalj.pcbuildlogger.data.repository

import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.davidturkalj.pcbuildlogger.PCBuildLogger
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.ui.viewmodels.ComponentsViewModel
import com.davidturkalj.pcbuildlogger.utilities.ComponentsCallback
import com.davidturkalj.pcbuildlogger.utilities.PcBuildsCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class Repository {

    private val TAG = "Repository"
    private val database = Firebase.database("https://pc-build-logger-default-rtdb.europe-west1.firebasedatabase.app/")
    private val auth = FirebaseAuth.getInstance()
    private val storageReference = Firebase.storage.reference
    private lateinit var componentsReference: DatabaseReference

    suspend fun register(email: String, password: String): Boolean {
        var isSuccessful = false
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            isSuccessful = true
            Toast.makeText(PCBuildLogger.ApplicationContext, "Registered successfully!", Toast.LENGTH_SHORT).show()
        } catch (exception: java.lang.Exception) {
            Toast.makeText(PCBuildLogger.ApplicationContext, exception.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return isSuccessful
    }

    suspend fun signIn(email: String, password: String): Boolean {
        var isSuccessful = false
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            isSuccessful = true
            Toast.makeText(PCBuildLogger.ApplicationContext, "Registered successfully!", Toast.LENGTH_SHORT).show()
        } catch (exception: java.lang.Exception) {
            Toast.makeText(PCBuildLogger.ApplicationContext, exception.message.toString(), Toast.LENGTH_SHORT).show()
        }
        return isSuccessful
    }

    fun signOut() {
        auth.signOut()
    }

    fun checkIfSignedIn(): Boolean {
        val currentUser: FirebaseUser? = auth.currentUser
        return currentUser != null
    }

    fun getUser(): FirebaseUser {
        return auth.currentUser!!
    }

    private fun getBuildReference(): DatabaseReference {
        return database.getReference("${getUser().uid}/builds")
    }

    fun getPcBuilds(callback: PcBuildsCallback) {
        getBuildReference().addValueEventListener(object : ValueEventListener {
            val pcBuilds = mutableListOf<PcBuild>()
            override fun onDataChange(snapshot: DataSnapshot) {
                pcBuilds.clear()
                for (snap in snapshot.children) {
                    val build = snap.getValue<PcBuild>()
                    if (build != null) {
                        build.key = snap.key
                        pcBuilds.add(build)
                    }
                }
                callback.getPcBuildsCallback(pcBuilds)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun createNewPcBuild(pcBuild: PcBuild) {
        val key = getBuildReference().push().key
        if (key != null) {
            getBuildReference().child(key).setValue(pcBuild)
        }
    }

    fun deletePcBuild(pcBuild: PcBuild) {
        getBuildReference().child(pcBuild.key.toString()).removeValue()
    }

    fun setComponentsReference(key: String?) {
        componentsReference = getBuildReference().child("$key/components")
    }

    fun getComponentsForBuild(callback: ComponentsCallback) {
        componentsReference.addValueEventListener(object : ValueEventListener {
            val components = mutableListOf<Component>()
            override fun onDataChange(snapshot: DataSnapshot) {
                components.clear()
                for (snap in snapshot.children) {
                    val component = snap.getValue<Component>()
                    if (component != null) {
                        component.key = snap.key
                        components.add(component)
                    }
                }
                callback.getComponentsCallback(components)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun createNewComponent(component: Component) {
        val key = componentsReference.push().key
        if (key != null) {
            componentsReference.child(key).setValue(component)
        }
    }

    fun deleteComponent(component: Component) {
        val regex = "\\d+.jpg".toRegex()
        val name = regex.find(component.imageLink.toString())?.value
        Log.d("Delete name: ", name.toString())
        storageReference.child(name.toString()).delete()
        componentsReference.child(component.key.toString()).removeValue()
    }

    suspend fun uploadImage(uri: Uri?, name: String) {
        if (uri != null) {
            storageReference.child(name).putFile(uri).await()
            ComponentsViewModel.imageLink = name
        }
    }

    suspend fun getImage(name: String): Uri? {
        return try {
            Log.d(TAG + "getImage", storageReference.child(name).toString())
            return storageReference.child(name).downloadUrl.await()
        } catch (e: Exception){
            Log.d(TAG + "getImage", e.message.toString())
            null
        }
    }

}