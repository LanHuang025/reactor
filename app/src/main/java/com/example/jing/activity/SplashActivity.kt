package com.example.jing.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.jing.SplashScreen
import com.example.jing.ui.theme.MyTheme
import com.huawei.agconnect.appmessaging.AGConnectAppMessaging

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window,false)
        super.onCreate(savedInstanceState)
        val appMessaging: AGConnectAppMessaging? = AGConnectAppMessaging.getInstance();
        appMessaging?.isDisplayEnable = false;
        setContent {
            MyTheme {
                Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    SplashScreen(this)
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onResume() {
        super.onResume()
        //finish()
    }
}