package com.davidturkalj.pcbuildlogger.utilities

import android.content.Context
import com.davidturkalj.pcbuildlogger.PCBuildLogger

class ResourceHelper {

    private val context: Context = PCBuildLogger.ApplicationContext

    fun getString(id: Int): String {
        return context.getString(id)
    }

}