package com.example.jing

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.jing.ui.theme.MyTheme
import com.example.jing.utils.CrashHandler

class MainActivity : ComponentActivity() {
    var context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        CrashHandler.INSTANCE.init(this)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        super.onCreate(savedInstanceState)
        //System.loadLibrary("ts")
        CrashHandler.INSTANCE.init(this)
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        setContent {
            MyTheme {
                Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    MainPage(this,clipboard)
                }
            }
        }
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        newConfig.let { super.onConfigurationChanged(it) }
    }
}