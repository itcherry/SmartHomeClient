package com.chernysh.smarthome

import  androidx.appcompat.app.AppCompatDelegate
import com.chernysh.smarthome.di.component.DaggerAppComponent
import com.chernysh.smarthome.utils.logging.CrashReportingTree
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import dagger.android.support.DaggerApplication
import io.fabric.sdk.android.Fabric
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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
        Fabric.with(this, Crashlytics())
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