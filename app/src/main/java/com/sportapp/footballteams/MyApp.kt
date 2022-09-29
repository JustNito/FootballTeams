package com.sportapp.footballteams

import android.app.Application
import com.sportapp.footballteams.di.DaggerAppComponent

class MyApp : Application() {
    val appComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}