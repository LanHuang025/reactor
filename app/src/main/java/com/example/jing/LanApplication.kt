package com.example.jing

import android.app.Application
import android.content.Context
import com.topjohnwu.superuser.ShellUtils

class MyApplication :Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ShellUtils.fastCmd("su")
    }
}