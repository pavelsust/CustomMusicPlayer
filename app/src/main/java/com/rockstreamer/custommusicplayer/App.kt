package com.rockstreamer.custommusicplayer

import android.app.Application

class App: Application() {


    companion object {
        private var instance: App?=null

        fun getContext():App{
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}