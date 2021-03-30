package com.zz.mycoolweatherapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class CoolWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context
    }
}