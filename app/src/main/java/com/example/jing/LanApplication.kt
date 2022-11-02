package com.example.jing

import ando.file.core.FileOperator
import android.app.Application

class LanApplication :Application(){
    override fun onCreate() {
        super.onCreate()
        FileOperator.init(this, BuildConfig.DEBUG)
    }
}