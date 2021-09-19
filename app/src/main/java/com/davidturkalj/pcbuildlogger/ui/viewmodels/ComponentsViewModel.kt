package com.davidturkalj.pcbuildlogger.ui.viewmodels

import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidturkalj.pcbuildlogger.PCBuildLogger
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.data.repository.Repository
import com.davidturkalj.pcbuildlogger.ui.activities.TabsActivity
import com.davidturkalj.pcbuildlogger.ui.fragments.ComponentsFragment.Companion.TAG
import com.davidturkalj.pcbuildlogger.utilities.ComponentsCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class ComponentsViewModel(private val repository: Repository) : ViewModel(), ComponentsCallback{

    private var _components: MutableLiveData<MutableList<Component>> = MutableLiveData<MutableList<Component>>()
    var components: LiveData<MutableList<Component>> = _components

    private fun createNewComponent(name: String, imageLink: String) {
        val component = Component(name, imageLink)
        _components.value?.add(component)
        repository.createNewComponent(component)
    }

    fun deleteComponent(component: Component) {
        _components.value?.remove(component)
        repository.deleteComponent(component)
    }

    fun getComponents() {
        repository.getComponentsForBuild(this)
    }

    fun setComponentReference(key: String?) {
        repository.setComponentsReference(key)
    }

    override fun getComponentsCallback(components: MutableList<Component>) {
        viewModelScope.launch {
            for (component in components) {
                component.imageLink = component.imageLink?.let { repository.getImage(it).toString() }
            }
            _components.value = components
        }
    }

    fun openDialog(activity: TabsActivity) {
        viewModelScope.launch {
            val bottomSheetDialog = BottomSheetDialog(activity)
            bottomSheetDialog.setContentView(R.layout.dialog_new_component)
            bottomSheetDialog.create()
            bottomSheetDialog.findViewById<Button>(R.id.btn_gallery)?.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                if (intent.resolveActivity(PCBuildLogger.ApplicationContext.packageManager) != null) {
                    startActivityForResult(activity, intent, REQUEST_SELECT_IMAGE,null)
                } else {
                    Log.d(TAG,"Image could not be selected!")
                }
            }
            bottomSheetDialog.findViewById<Button>(R.id.btn_camera)?.setOnClickListener {

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(PCBuildLogger.ApplicationContext.packageManager) != null) {
                    startActivityForResult(activity, intent, REQUEST_TAKE_PHOTO,null)
                } else {
                    Log.d(TAG,"Photo could not be taken!")
                }
            }
            bottomSheetDialog.findViewById<Button>(R.id.btn_cancel)?.setOnClickListener { bottomSheetDialog.dismiss() }
            bottomSheetDialog.findViewById<Button>(R.id.btn_confirm)?.setOnClickListener {
                val componentName = bottomSheetDialog.findViewById<EditText>(R.id.et_new_component_name)!!.text.toString()
                if (imageLink != "" && componentName != "") {
                    createNewComponent(componentName, imageLink)
                    imageLink = ""
                    bottomSheetDialog.dismiss()
                } else {
                    Toast.makeText(PCBuildLogger.ApplicationContext, "Image link and name needed!", Toast.LENGTH_SHORT).show()
                }
            }
            bottomSheetDialog.show()
        }
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 0
        const val REQUEST_SELECT_IMAGE = 1

        var imageLink: String = ""
    }

}