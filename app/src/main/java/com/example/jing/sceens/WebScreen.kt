package com.example.jing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.GeolocationPermissions
import android.webkit.JsResult
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.KeyboardArrowLeft
import androidx.compose.material.icons.twotone.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.jing.ui.theme.Pink40
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.rememberWebViewState


@SuppressLint("SetJavaScriptEnabled", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebScreen(
    navController: NavController,
    flag: String,
    context: Context,
    webViewNavigator: WebViewNavigator,
    rememberUri: MutableState<String>,
) {
    Translatebar()
    val LAN = flag
    val AS = "https://developer.android.google.cn/studio"
    val compose = "https://developer.android.google.cn/jetpack/compose"
    val help = "https://support.qq.com/product/441318"
    val insole = "https://m.saolei123.com/"
    val CLASS = "http://dekt.jxutcm.edu.cn/scParticipantController.do?main#idtop"
    val epidemic = "https://www.jiandaoyun.com" +
            "/app/5e37f1777d3dc900065d3aed" +
            "/entry/5e395fd66ceab20006a25e48#" +
            "/app/5e37f1777d3dc900065d3aed/form" +
            "/5e395fd66ceab20006a25e48"
    val icpc = "https://board.xcpcio.com/"
    val cf = "https://cromarmot.github.io/cfannual/#/"
    val dev = "https://kaifa.baidu.com/"
    val mdui = "https://www.mdui.org/"
    val oiwiki = "https://oi-wiki.org/"
    val ctfwiki = "https://ctf-wiki.org/"
    val lan = "https://LanHuang025.github.io"
    val translate = "https://translate.amz.wang/"
    val aibot = "https://gpt.chatapi.art/"
    val microsoft_doc = "https://learn.microsoft.com/zh-cn/windows/apps/desktop/"
    val teacher_system =
        "https://jwxt.jxutcm.edu.cn/jwglxt/xtgl/index_initMenu.html?jsdm=xs&_t=1670772317365"
    val workcenter = "https://www.jiandaoyun.com/signin?redirect_uri=%2Fdashboard#/"
    val acmer = "http://acmer.info/"
    val url by remember {
        mutableStateOf(
            if (flag == "as") AS else
                if (flag == "compose") compose else if (flag == "help") help
                else if (flag == "insole") insole else if (flag == "class")
                    CLASS
                else if (flag == "epidemic") epidemic
                else if (flag == "icpc") icpc
                else if (flag == "cf") cf
                else if (flag == "dev") dev
                else if (flag == "mdui") mdui
                else if (flag == "oiwiki") oiwiki
                else if (flag == "ctfwiki") ctfwiki
                else if (flag == "lan") lan
                else if (flag == "translate") translate
                else if (flag == "aibot") aibot
                else if (flag == "microsoft") microsoft_doc
                else if (flag == "teacher") teacher_system
                else if (flag == "workcenter") workcenter
                else if (flag == "acmer") acmer
                else ""
        )
    }
    var isShowA by remember {
        mutableStateOf(false)
    }
    var isShowB by remember {
        mutableStateOf(false)
    }
    var messageX by remember {
        mutableStateOf("")
    }
    val result: JsResult? = null
    var resultX by remember {
        mutableStateOf(result)
    }
    if (isShowA) {
        AlertDialog(onDismissRequest = {
            isShowA = false
            resultX?.cancel()
        },
            title = {
                Text(text = "反应堆")
            },
            text = {
                Text(text = messageX)
            },
            confirmButton = {
                TextButton(onClick = {
                    isShowA = false
                    resultX?.cancel()
                }) {
                    Text(text = "确定", color = Color.Red)
                }
            }
        )
    }
    if (isShowB) {
        AlertDialog(onDismissRequest = {
            isShowB = false
            resultX?.cancel()
        },
            title = {
                Text(text = "反应堆")
            },
            text = {
                Text(text = messageX)
            },
            confirmButton = {
                TextButton(onClick = {
                    isShowB = false
                    resultX?.confirm()
                }) {
                    Text(text = "确定", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isShowB = false
                    resultX?.cancel()
                }) {
                    Text(text = "取消", color = Color.Blue)
                }
            }
        )
    }
    val state = rememberWebViewState(url = url)
    rememberUri.value = state.content.getCurrentUrl()!!
    val loadingState = state.loadingState
    val webchromeClient = remember {
        object : AccompanistWebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                val chooseIntent = Intent.createChooser(i, "请选择你的图片")
                context.startActivity(chooseIntent)
                return false
            }

            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?
            ) {
                callback!!.invoke(origin, true, false)
                super.onGeolocationPermissionsShowPrompt(origin, callback)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                messageX = message!!
                resultX = result
                isShowA = true
                return true
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                messageX = message!!
                resultX = result
                isShowB = true
                return true
            }
        }
    }
    val webclient = remember {
        object : AccompanistWebViewClient() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return if (url.startsWith("http") || url.startsWith("https")) { //http和https协议开头的执行正常的流程
                    super.shouldInterceptRequest(view, url)
                } else {  //其他的URL则会开启一个Acitity然后去调用原生APP
                    val goto = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(goto)
                    return null
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.loadUrl("javascript:\$('#username').val('202201014028');")
                view?.loadUrl("javascript:\$('#password_text').val('20031002aA1~');")
                view?.loadUrl("javascript:\$('#rememberMe').prop(\"checked\",true);")
                //view?.loadUrl("javascript:alert(\"${url}\");")
                /*if (url=="http://dekt.jxutcm.edu.cn/loginController.do?mlogin"){
                    view?.loadUrl("javascript:document.getElementById(\"app\").getElementsByTagName(\"input\")[0].value=\"202201014028\";")
                 view?.loadUrl("javascript:document.getElementById(\"app\").getElementsByTagName(\"input\")[1].value=\"zyy@#021734\";")
                }
                view?.loadUrl("javascript:alert(\"${url}\");")*/
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = if (url == help) "反馈中心"
                    else if (url == AS) "AndroidStudio"
                    else if (url == compose) "Jetpack Compose"
                    else if (url == insole) "扫雷"
                    else if (url == CLASS) "第二课堂"
                    else if (url == epidemic) "疫情填报"
                    else if (url == icpc) "ICPC榜单"
                    else if (url == cf) "Codeforces年度报告"
                    else if (url == dev) "开发者搜索"
                    else if (url == mdui) "mdui文档"
                    else if (url == oiwiki) "OI Wiki"
                    else if (url == ctfwiki) "CTF Wiki"
                    else if (url == lan) "我的小窝"
                    else if (url == translate) "谷歌翻译"
                    else if (url == aibot) "AI机器人"
                    else if (url == microsoft_doc) "WinUI文档"
                    else if (url == teacher_system) "教务系统"
                    else if (url == workcenter) "办事大厅"
                    else if (url == acmer) "Acmer的俱乐部"
                    else url, overflow = TextOverflow.Ellipsis
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("MainScreen")
                    }) {
                        Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("WebScreen/$flag")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Home, contentDescription = null)
                    }
                    IconButton(onClick = {
                        webViewNavigator.navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        webViewNavigator.navigateForward()
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                if (loadingState is LoadingState.Loading) {
                    LinearProgressIndicator(
                        progress = loadingState.progress,
                        modifier = Modifier.fillMaxWidth(),
                        color = Pink40,
                        trackColor = Pink40
                    )
                }
                WebView(
                    captureBackPresses = true,
                    state = state,
                    onCreated = {
                        it.apply {
                            settings.apply {
                                displayZoomControls = false
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                loadWithOverviewMode = true
                                javaScriptCanOpenWindowsAutomatically = true
                                setGeolocationEnabled(true)
                                databaseEnabled = true
                                builtInZoomControls = true
                                setSupportZoom(true)
                                useWideViewPort = true
                                allowContentAccess = true
                                allowFileAccess = true
                                allowFileAccessFromFileURLs = true
                                saveFormData = true
                                savePassword(
                                    "http://dekt.jxutcm.edu.cn/loginController.do?mlogin",
                                    "202201014028", "zyy@#021734"
                                )
                            }
                        }
                    },
                    modifier = Modifier.weight(1f), chromeClient = webchromeClient,
                    client = webclient,
                    navigator = webViewNavigator
                )
            }
            if (loadingState is LoadingState.Loading)
                CircularProgressIndicator()
        }
    }
    CookieSyncManager.createInstance(context);
    CookieSyncManager.getInstance().startSync();
    CookieManager.getInstance();
}