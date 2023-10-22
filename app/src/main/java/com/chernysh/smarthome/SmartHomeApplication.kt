package com.chernysh.smarthome

import  androidx.appcompat.app.AppCompatDelegate
import com.chernysh.smarthome.di.component.DaggerAppComponent
import com.chernysh.smarthome.utils.logging.CrashReportingTree
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.DaggerApplication
import leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject

class SmartHomeApplication : DaggerApplication(), HasServiceInjector, HasActivityInjector {
    @Inject lateinit var mDebugTimberTree: Timber.DebugTree
    @Inject lateinit var mReleaseTimberTree: CrashReportingTree

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initThirdParties()
    }

    private fun initThirdParties() {
        FirebaseApp.initializeApp(this);
        if (BuildConfig.DEBUG) {
            // AndroidDevMetrics.initWith(this);
            Timber.plant(mDebugTimberTree)
        } else {
            Timber.plant(mReleaseTimberTree)
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}