package com.cypress.xingcodechallengeapplication

import android.app.Application
import com.cypress.xingcodechallengeapplication.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@MyApplication)
        }
    }

}