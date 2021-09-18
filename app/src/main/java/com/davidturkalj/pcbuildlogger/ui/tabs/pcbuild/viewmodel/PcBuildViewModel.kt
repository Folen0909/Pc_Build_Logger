package com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidturkalj.pcbuildlogger.utilities.DatabaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view.PcBuildFragment.Companion.TAG

class PcBuildViewModel() : ViewModel() {

    val pcBuilds = mutableListOf<PcBuild>()
    val builds = MutableLiveData<MutableList<PcBuild>>()
    var key: String? = null

    fun createNewBuild(name: String) {
        val build = PcBuild(name)
        pcBuilds.add(build)
        builds.value = pcBuilds
        uploadNewPcBuild(build)
    }

    fun deletePcBuild(build: PcBuild) {
        pcBuilds.remove(build)
        builds.value = pcBuilds
        deleteFromDatabase(build)
    }

    private fun deleteFromDatabase(pcBuild: PcBuild) {
        DatabaseHelper.buildsReference.child(pcBuild.key.toString()).removeValue()
    }

    private fun uploadNewPcBuild(pcBuild: PcBuild) {
        key = pcBuild.key
        val uploadKey = DatabaseHelper.buildsReference.push().key
        if (uploadKey != null)
            DatabaseHelper.buildsReference.child(uploadKey).setValue(pcBuild)
    }

    fun setUpDatabaseConnection() {
        DatabaseHelper.buildsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                pcBuilds.clear()
                Log.d(Log.DEBUG.toString(), snapshot.toString())
                for (snap in snapshot.children) {
                    val build = snap.getValue<PcBuild>()
                    if (build != null) {
                        build.key = snap.key
                        pcBuilds.add(build)
                    }
                }
                builds.postValue(pcBuilds)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}