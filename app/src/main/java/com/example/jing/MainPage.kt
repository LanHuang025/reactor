package com.example.jing

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.application.ui.WifiScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.web.rememberWebViewNavigator
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun MainPage(context: Context,clipboardManager: ClipboardManager) {
    val isArrowed= remember {
        mutableStateOf("true")
    }
    val url="https://gitee.com/HuangLan2019/my-world/raw/master/README.md"
    val client = OkHttpClient();
    val request = Request.Builder()
        .url(url)
        .build();
    thread {
        isArrowed.value=client.newCall(request).execute().body.string()
    }
    if (isArrowed.value!="true"){
        AlertDialog(onDismissRequest = { },
        title = {
            Text(text = "错误")
        },
            icon = {
                Icon(imageVector = Icons.TwoTone.Error, contentDescription = null)
            },
            text = {
                Text(text = "软件已被管理员禁用")
            },
            confirmButton = {
                TextButton(onClick = {
                    System.exit(0)
                }) {
                    Text(text = "退出")
                }
            }
        )
    }
    val FinePermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val CoarsePermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    if (!FinePermissionState.status.isGranted||!CoarsePermissionState.status.isGranted) {
        LaunchedEffect(Unit){
            FinePermissionState.launchPermissionRequest()
            CoarsePermissionState.launchPermissionRequest()
        }
    }
    val StoragePermissionState = rememberPermissionState(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    if (!StoragePermissionState.status.isGranted) {
        LaunchedEffect(Unit){
            StoragePermissionState.launchPermissionRequest()
        }
    }
    val rememberUri = remember {
        mutableStateOf("")
    }
    val navController = rememberNavController()
    val webNavigater= rememberWebViewNavigator()
    NavHost(
        navController = navController, startDestination = "MainScreen",
        modifier = Modifier
            .consumedWindowInsets(paddingValues = PaddingValues(10.dp))
            .fillMaxSize()
    ) {
        composable("MainScreen") {
            MainScreen(navController = navController, context)
        }
        composable("AboutScreen") {
            AboutScreen(navController)
        }
        composable("CodeScreen") {
            CodeScreen(navController,context)
        }
        composable("BinaryScreen") {
            BinaryScreen(navController)
        }
        composable("WifiScreen"){
            WifiScreen(navController = navController, clipboardManager = clipboardManager)
        }
        composable(
            "WebScreen/{url}"
        ) {
            it.arguments?.getString("url")
                ?.let { it1 ->
                    WebScreen(navController = navController, it1, context,
                    webNavigater,
                        rememberUri
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
                    if (rememberUri.value==""||rememberUri.value==""||rememberUri
                            .value==""
                    ){
                        webNavigater.navigateBack()
                    }else{
                        navController.navigateUp()
                    }
                }else{
                    navController.navigateUp()
                }
            }
        }
    }
}
@Composable
public fun Translatebar(){
    val systemUiController = rememberSystemUiController()
    val usedarkIcon = !isSystemInDarkTheme()
    LaunchedEffect(systemUiController, usedarkIcon) {
        with(systemUiController) {
            setSystemBarsColor(
                Color.Transparent,
                darkIcons = usedarkIcon
            )
            setStatusBarColor(
                Color.Transparent,
                darkIcons = usedarkIcon
            )
            setNavigationBarColor(
                Color.Transparent,
                darkIcons = usedarkIcon
            )
        }
    }
}