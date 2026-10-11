package com.example.mytodo

import android.app.Application

class TodoApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate(){
        super.onCreate()
        container = AppContainer(this)
    }
}