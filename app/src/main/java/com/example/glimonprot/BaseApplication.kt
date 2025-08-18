package com.example.glimonprot

import android.app.Application
import com.example.glimonprot.di.components.AppComponent
import com.example.glimonprot.di.components.DaggerAppComponent
import timber.log.Timber


class BaseApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
      //  Network.init(this)
        Timber.plant(Timber.DebugTree())

    }

}
