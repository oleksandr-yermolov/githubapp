package com.oyermolov.githubapp

import android.app.Application
import com.oyermolov.githubapp.di.AppModule
import com.oyermolov.githubapp.di.ApplicationComponent
import com.oyermolov.githubapp.di.DaggerApplicationComponent

open class GithubApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()
        appComponent.inject(this)
    }

    protected open fun createAppComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}