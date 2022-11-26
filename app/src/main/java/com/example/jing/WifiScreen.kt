package com.example.application.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jing.data.WifiData
import com.example.jing.utils.XmlPaser
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.topjohnwu.superuser.Shell
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// TODO: 1.点击复制密码
// TODO: 2.搜索过滤 8.30.2022 jing
fun WifiScreen(navController: NavController, clipboardManager: ClipboardManager) {
    val viewModel: MyViewModel = viewModel()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val Path = "/data/misc/wifi/WifiConfigStore.xml"
    var result = Shell.cmd("cat $Path > /sdcard/wifitest.xml").exec()
    var list = XmlPaser().maplist(pathname = "/sdcard/wifitest.xml")
    val dialog = remember {
        mutableStateOf(false)
    }
    if (dialog.value) {
        AlertDialog(onDismissRequest = { dialog.value = false },
            title = { Text(text = "提示") },
            text = { Text(text = result.toString(), modifier = Modifier.fillMaxSize()) },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialog.value = false
                    }
                ) {
                    Text("确定")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialog.value = false
                    }
                ) {
                    Text("取消")
                }
            })
    }
    Scaffold(modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Wifi密码查看器 (${list.size})")
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(imageVector = Icons.TwoTone.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                swipeEnabled = false,
                clipIndicatorToPadding = true,
                onRefresh = { viewModel.refresh() },
                modifier = Modifier.padding(paddingValues = it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(), state = rememberLazyListState()
                ) {
                    items(list.size) {
                        materialCard(
                            list.get(list.size - it - 1),
                            clipboardManager,
                            LocalContext.current
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.refresh()
                },
                containerColor = Color(0xFF80DEEA),
            ) {
                Icon(imageVector = Icons.TwoTone.Search, contentDescription = null)
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                this.AnimatedVisibility(visible = true) {
                    Text(text = "刷新", color = Color.Black)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun materialCard(wifiData: WifiData, clipboardManager: ClipboardManager, context: Context) {
    OutlinedCard(
        modifier = Modifier
            .clickable {
                val clip = ClipData.newPlainText("密码", wifiData.password)
                clipboardManager.setPrimaryClip(clip)
                Toast
                    .makeText(context, "密码已复制到剪贴板", Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            hang(name = "WIFI名称:", content = wifiData.ssid)
            hang(name = "WIFI密码:", content = wifiData.password)
            hang(name = "WIFI创建时间:", content = wifiData.createtime)
        }
    }
}

@Composable
fun hang(name: String, content: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = name, fontFamily = FontFamily.Monospace)
        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        Text(text = content)
    }
}

class MyViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh() {
        // This doesn't handle multiple 'refreshing' tasks, don't use this
        viewModelScope.launch {
            // A fake 2 second 'refresh'
            _isRefreshing.emit(true)
            delay(1000)
            _isRefreshing.emit(false)
        }
    }
}