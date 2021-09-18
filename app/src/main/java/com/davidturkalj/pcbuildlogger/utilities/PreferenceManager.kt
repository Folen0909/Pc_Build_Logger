package com.davidturkalj.pcbuildlogger.utilities

import android.content.Context
import com.davidturkalj.pcbuildlogger.PCBuildLogger

class PreferenceManager {

    companion object {
        const val PREFS_FILE = "MyPreferences"
        const val USER_ID = "user_id"
    }

    fun saveUserId(userId: String) {
        val sharedPreference = PCBuildLogger.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreference.edit()
        editor.putString(USER_ID, userId)
        editor.apply()
    }

    fun retrieveUserId() : String {
        val sharedPreference = PCBuildLogger.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreference.getString(USER_ID, "unknown").toString()
    }
}