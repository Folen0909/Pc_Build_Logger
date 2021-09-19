package com.davidturkalj.pcbuildlogger

import android.app.Application
import android.content.Context
import com.davidturkalj.pcbuildlogger.di.appModule
import com.davidturkalj.pcbuildlogger.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PCBuildLogger : Application() {

    companion object {
        lateinit var ApplicationContext : Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this

        startKoin {
            androidContext(this@PCBuildLogger)
            modules(listOf(appModule, viewModelModule))
        }
    }
}