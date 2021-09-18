package com.davidturkalj.pcbuildlogger.utilities

interface DialogListener {
    fun onDialogPositiveClick(name: String)
    fun onDialogPositiveClick(name: String, imageLink: String)
}