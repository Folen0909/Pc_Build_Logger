package com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.utilities.DialogListener
class PcBuildNewDialog : DialogFragment() {

    private lateinit var listener: DialogListener
    lateinit var etBuildName: EditText

    fun show(manager: FragmentManager, tag: String?, listener: DialogListener) {
        super.show(manager, tag)
        setListener(listener)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.dialog_new_build, null)
        if (view != null) {
            etBuildName = view.findViewById(R.id.et_new_build_name)
        }
        builder.setView(view)
            .setCancelable(true)
            .setTitle("New build")
            .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
                Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
            }.setPositiveButton("Add") { _, _ ->
                if (etBuildName.text.toString().trim { it <= ' ' } != "") {
                    run {
                        listener.onDialogPositiveClick(etBuildName.text.toString())
                    }
                } else {
                    Toast.makeText(activity, "Error! Build name needed!", Toast.LENGTH_SHORT).show()
                }
            }
        return builder.create()
    }

    private fun setListener(listener: DialogListener) {
        this.listener = listener
    }
}