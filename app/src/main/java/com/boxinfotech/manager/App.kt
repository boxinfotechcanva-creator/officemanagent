package com.boxinfotech.manager

import android.app.Application
import com.boxinfotech.manager.notifications.createAssignmentChannel

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Ensure notification channel exists
        createAssignmentChannel(this)
    }
}