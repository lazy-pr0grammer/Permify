package com.aylax.permify

import android.app.Application
import android.content.Context
import com.google.android.material.color.DynamicColors

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    companion object {
        lateinit var context: Context
    }
}