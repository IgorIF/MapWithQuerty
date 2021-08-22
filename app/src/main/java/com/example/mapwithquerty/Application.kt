package com.example.mapwithquerty

import android.app.Application
import com.example.mapwithquerty.di.ApplicationComponent
import com.example.mapwithquerty.di.DaggerApplicationComponent

class Application: Application() {

    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()

}