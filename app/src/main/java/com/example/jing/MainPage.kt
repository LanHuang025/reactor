package com.example.jing

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.rememberWebViewNavigator


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
    val webNavugater= rememberWebViewNavigator()
    NavHost(
        navController = navController, startDestination = "MainScreen"
    ) {
        composable("MainScreen") {
            MainScreen(navController = navController, context)
        }
        composable("AboutScreen") {
            AboutScreen(navController)
        }
        composable(
            "WebScreen/{url}"
        ) {
            it.arguments?.getString("url")
                ?.let { it1 ->
                    WebScreen(navController = navController, it1, context,
                    webNavugater
                        )
                }
        }
    }
    var firstPressedTime = 0L
    val toggle = remember {
        mutableStateOf(false)
    }
    if (toggle.value) {
        AlertDialog(onDismissRequest = { toggle.value = false },
            title = { Text(text = "当前route") },
            text = {
                navController.currentDestination?.let { it1 -> it1.route?.let { it2 -> Text(text = it2) } }
            },
            confirmButton = {
                TextButton(onClick = { toggle.value = false }) {
                    Text(text = "确定")
                }
            }
        )
    }
    BackHandler(true) {
        navController.currentDestination?.let { it1 ->
            it1.route?.let { it2 ->
                if (it2 == "MainScreen") {
                    navController.currentDestination?.route
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
                } else if (it2 == "WebScreen/{url}"){
                    webNavugater.navigateBack()
                }else{
                    navController.navigateUp()
                }
            }
        }
    }
}