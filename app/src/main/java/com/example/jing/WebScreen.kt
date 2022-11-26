package com.example.jing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.GeolocationPermissions
import android.webkit.ValueCallback
import android.webkit.WebView
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.jing.ui.theme.Pink40
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(navController: NavController
              ,flag:String
              ,context:Context,
              webViewNavigator: WebViewNavigator,
              rememberUri:MutableState<String>,
){
    Translatebar()
    val AS="https://developer.android.google.cn/studio"
    val compose="https://developer.android.google.cn/jetpack/compose"
    val help="https://support.qq.com/product/441318"
    val insole="https://m.saolei123.com/"
    val CLASS="http://dekt.jxutcm.edu.cn/scParticipantController.do?main#idtop"
    val epidemic="https://www.jiandaoyun.com" +
            "/app/5e37f1777d3dc900065d3aed" +
            "/entry/5e395fd66ceab20006a25e48#" +
            "/app/5e37f1777d3dc900065d3aed/form" +
            "/5e395fd66ceab20006a25e48"
    val icpc="https://board.xcpcio.com/"
    val cf="https://cromarmot.github.io/cfannual/#/"
    val dev="https://kaifa.baidu.com/"
    val mdui="https://www.mdui.org/"
    val oiwiki="https://oi-wiki.org/"
    val ctfwiki="https://ctf-wiki.org/"
    val url by remember {
        mutableStateOf(
            if (flag=="as") AS else
                if (flag=="compose") compose else if (flag=="help") help
                     else if (flag=="insole") insole else if(flag=="class")
                        CLASS
                else if(flag=="epidemic") epidemic
                else if(flag=="icpc") icpc
        else if (flag=="cf") cf
        else if (flag=="dev") dev
        else if (flag=="mdui") mdui
        else if (flag=="oiwiki") oiwiki
        else if (flag=="ctfwiki") ctfwiki
        else ""
        )
    }
    val state = rememberWebViewState(url = url)
    rememberUri.value=state.content.getCurrentUrl()!!
    val loadingState = state.loadingState
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = if (url==help) "反馈中心"
                else if (url==AS) "AndroidStudio"
                else if (url==compose) "Jetpack Compose"
                else if (url==insole) "扫雷"
                else if (url==CLASS) "第二课堂"
                    else if (url==epidemic) "疫情填报"
                else if (url==icpc) "ICPC榜单"
                    else if (url==cf) "Codeforces年度报告"
                    else if (url==dev) "开发者搜索"
                else if (url==mdui) "mdui文档"
                else if (url==oiwiki) "OI Wiki"
                else if (url==ctfwiki) "CTF Wiki"
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
                val webClient = remember {
                    object : AccompanistWebChromeClient() {
                        override fun onShowFileChooser(
                            webView: WebView?,
                            filePathCallback: ValueCallback<Array<Uri>>?,
                            fileChooserParams: FileChooserParams?
                        ): Boolean {
                            val i=Intent(Intent.ACTION_GET_CONTENT)
                            i.addCategory(Intent.CATEGORY_OPENABLE)
                            i.type="image/*"
                            val chooseIntent=Intent.createChooser(i,"请选择你的图片")
                            context.startActivity(chooseIntent)
                            return false
                        }

                        override fun onGeolocationPermissionsShowPrompt(
                            origin: String?,
                            callback: GeolocationPermissions.Callback?
                        ) {
                                callback!!.invoke(origin,true,false)
                            super.onGeolocationPermissionsShowPrompt(origin, callback)
                        }
                    }
                }
                WebView(
                    captureBackPresses = true,
                    state = state,
                    onCreated = {
                        it.apply {
                            settings.apply {
                                displayZoomControls=false
                                javaScriptEnabled = true
                                domStorageEnabled=true
                                loadWithOverviewMode=true
                                javaScriptCanOpenWindowsAutomatically=true
                                setGeolocationEnabled(true)
                                databaseEnabled=true
                                builtInZoomControls=true
                                setSupportZoom(true)
                                useWideViewPort=true
                            }
                        }

                    },
                    modifier = Modifier.weight(1f)
                , chromeClient = webClient,
                    navigator = webViewNavigator
                )
            }
            if (loadingState is LoadingState.Loading)
            CircularProgressIndicator()
        }
    }
}