package com.davidturkalj.pcbuildlogger.ui.components.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidturkalj.pcbuildlogger.utilities.DatabaseHelper
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.ui.components.view.ComponentsFragment
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view.PcBuildFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ComponentsViewModel() : ViewModel() {

    private var buildKey: String = ""
    val userComponents = mutableListOf<Component>()
    val components = MutableLiveData<MutableList<Component>>()

    fun createNewComponent(name: String, imageLink: String) {
        val component = Component(name, imageLink)
        userComponents.add(component)
        components.value = userComponents
        uploadNewComponent(component)
    }

    fun deleteComponent(component: Component) {
        DatabaseHelper.componentsReference.child("$buildKey/components/${component.key}").removeValue()
    }

    fun setBuildKey(key: String) {
        this.buildKey = key;
    }

    private fun uploadNewComponent(component: Component) {
        val uploadKey = DatabaseHelper.componentsReference.push().key
        if (uploadKey != null)
            DatabaseHelper.componentsReference.child(uploadKey).setValue(component)
    }

    fun setUpDatabaseConnection() {
        DatabaseHelper.changeBuild(buildKey)
        DatabaseHelper.componentsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userComponents.clear()
                Log.d(Log.DEBUG.toString(), snapshot.toString())
                for (snap in snapshot.children) {
                    val component = snap.getValue<Component>()
                    if (component != null) {
                        component.key = snap.key
                        userComponents.add(component)
                    }
                }
                components.postValue(userComponents)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ComponentsFragment.TAG, "Failed to read value.", error.toException())
            }
        })
    }

}