package com.example.mobiletestzemoga

import android.app.Application
import com.example.mobiletestzemoga.core.di.androidModule
import com.example.mobiletestzemoga.core.di.mLocalModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(mLocalModules, androidModule))
        }
    }
}