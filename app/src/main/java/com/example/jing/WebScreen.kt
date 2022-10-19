package com.example.jing

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jing.ui.theme.Pink40
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(navController: NavController){
    val url by remember { mutableStateOf("https://support.qq.com/product/441318") }
    val state = rememberWebViewState(url = url)
    val loadingState = state.loadingState
    BackHandler(true) {
        navController.navigate("MainScreen")
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "反馈中心")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column (Modifier.padding(it)){
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = Pink40,
                    trackColor = Pink40
                )
            }
            WebView(
                state = state,
                onCreated = { it.settings.javaScriptEnabled = true
                            },
                modifier = Modifier.weight(1f)
            )
        }
    }
}