package com.example.glimonprot

import android.app.Application
import timber.log.Timber


class BaseApplication  : Application() {

//    val component: AppComponent by lazy {TODO()
//        DaggerAppComponent.factory()
//            .create(this)
//    }

    override fun onCreate() {
        super.onCreate()
      //  Network.init(this)
        Timber.plant(Timber.DebugTree())

    }

}
