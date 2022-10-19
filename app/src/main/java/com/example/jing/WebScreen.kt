package com.example.jing

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.jing.ui.theme.Pink40
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(navController: NavController,flag:String){
    val url by remember {
        mutableStateOf(
            if (flag=="as") "https://developer.android.google.cn/studio" else
                if (flag=="compose") "https://developer.android.google.cn/jetpack/compose" else
                    "https://support.qq.com/product/441318"
        )
    }
    val state = rememberWebViewState(url = url)
    val loadingState = state.loadingState
    BackHandler(true) {
        navController.navigateUp()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = if (url=="https://support.qq.com/product/441318") "反馈中心"
                else if (url=="https://developer.android.google.cn/studio") "AndroidStudio"
                else if (url=="https://developer.android.google.cn/jetpack/compose") "Jetpack Compose"
                else url, overflow = TextOverflow.Ellipsis
                )
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
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
            ){
            Column (
                Modifier
                    .padding(it)
                    .fillMaxSize()){
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
            if (loadingState is LoadingState.Loading)
            CircularProgressIndicator()
        }
    }
}