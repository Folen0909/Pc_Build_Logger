package com.davidturkalj.pcbuildlogger.ui.viewmodels

import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.data.repository.Repository
import com.davidturkalj.pcbuildlogger.ui.activities.TabsActivity
import com.davidturkalj.pcbuildlogger.utilities.PcBuildsCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class PcBuildViewModel(private val repository: Repository) : ViewModel(), PcBuildsCallback{

    private var _pcBuilds: MutableLiveData<MutableList<PcBuild>> = MutableLiveData<MutableList<PcBuild>>()
    var pcBuilds: LiveData<MutableList<PcBuild>> = _pcBuilds
    var key: String? = null

    private fun createNewBuild(name: String) {
        val build = PcBuild(name)
        _pcBuilds.value?.add(build)
        repository.createNewPcBuild(build)
    }

    fun deletePcBuild(build: PcBuild) {
        _pcBuilds.value?.remove(build)
        repository.deletePcBuild(build)
    }

    fun getPcBuilds() {
       repository.getPcBuilds(this)
    }

    override fun getPcBuildsCallback(pcBuilds: MutableList<PcBuild>) {
        _pcBuilds.value = pcBuilds
    }

    fun openDialog(activity: TabsActivity) {
        viewModelScope.launch {
            val bottomSheetDialog = BottomSheetDialog(activity)
            bottomSheetDialog.setContentView(R.layout.dialog_new_build)
            bottomSheetDialog.create()
            bottomSheetDialog.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener { bottomSheetDialog.onBackPressed() }
            bottomSheetDialog.findViewById<Button>(R.id.btn_confirm)?.setOnClickListener {
                val buildName = bottomSheetDialog.findViewById<EditText>(R.id.et_new_build_name)!!.text.toString()
                createNewBuild(buildName)
                bottomSheetDialog.onBackPressed()
            }
            bottomSheetDialog.show()
        }
    }
}