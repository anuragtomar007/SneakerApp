package com.anurag.sneakerapp

import android.app.Application
import com.anurag.sneakerapp.di.AppComponent
import com.anurag.sneakerapp.di.AppModule
import com.anurag.sneakerapp.di.DaggerAppComponent

class App : Application(){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
