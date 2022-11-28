package com.oyermolov.githubapp.di

import android.app.Activity
import android.app.Application
import android.content.Context
import com.oyermolov.githubapp.ui.MainActivity
import dagger.Component

@Component(modules = [
    AppModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    DataSourcesModule::class,
    RepositoriesModule::class
])
interface ApplicationComponent {
    fun inject(application: Application)
    fun inject(activity: MainActivity)
    fun context(): Context
    fun application(): Application
}
