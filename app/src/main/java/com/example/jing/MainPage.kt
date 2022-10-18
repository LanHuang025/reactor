package com.example.jing

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MainPage(context: Context) {
    val systemUiController = rememberSystemUiController()
    val usedarkIcon = !isSystemInDarkTheme()
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black
    LaunchedEffect(systemUiController, usedarkIcon) {
        systemUiController.setSystemBarsColor(
            color,
            darkIcons = usedarkIcon
        )
        with(systemUiController) {
            setStatusBarColor(
                color,
                darkIcons = usedarkIcon
            )
            setNavigationBarColor(
                color,
                darkIcons = usedarkIcon
            )
        }
    }
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "MainScreen"
    ) {
        composable("MainScreen") {
            MainScreen(navController = navController,context)
        }
        composable("AboutScreen"){
            AboutScreen(navController)
        }
        composable("WebScreen"){
            WebScreen(navController = navController)
        }
    }
    var firstPressedTime = 0L
    BackHandler(true) {
        firstPressedTime = if (System.currentTimeMillis() - firstPressedTime < 2000) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            context.startActivity(intent)
            0L
        } else {
            val toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
            toast.setText("再按一次退出")
            toast.show()
            System.currentTimeMillis()
        }
    }
}