package com.example.jing

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AcUnit
import androidx.compose.material.icons.twotone.AddChart
import androidx.compose.material.icons.twotone.Android
import androidx.compose.material.icons.twotone.Blender
import androidx.compose.material.icons.twotone.BusinessCenter
import androidx.compose.material.icons.twotone.CalendarToday
import androidx.compose.material.icons.twotone.Call
import androidx.compose.material.icons.twotone.Co2
import androidx.compose.material.icons.twotone.Code
import androidx.compose.material.icons.twotone.CodeOff
import androidx.compose.material.icons.twotone.CurrencyExchange
import androidx.compose.material.icons.twotone.Dock
import androidx.compose.material.icons.twotone.Help
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material.icons.twotone.Microwave
import androidx.compose.material.icons.twotone.MinorCrash
import androidx.compose.material.icons.twotone.OilBarrel
import androidx.compose.material.icons.twotone.SettingsSystemDaydream
import androidx.compose.material.icons.twotone.Transform
import androidx.compose.material.icons.twotone.Translate
import androidx.compose.material.icons.twotone.WebStories
import androidx.compose.material.icons.twotone.Wifi
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow
import com.huawei.agconnect.crash.AGConnectCrash
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, context: Context) {
    Translatebar()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectItem = remember {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(), drawerContent = {
            ModalDrawerSheet(windowInsets = WindowInsets(0, 0, 0, 0)) {
                Image(painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .height(200.dp))
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(label = { Text(text = "主页") },
                    selected = 0 == selectItem.value,
                    onClick = {
                        scope.launch {
                            if (selectItem.value == 0) {
                                drawerState.close()
                            } else {
                                selectItem.value = 0
                            }
                        }
                    },
                    shape = CircleShape,
                    icon = {
                        Icon(imageVector = Icons.TwoTone.Home, contentDescription = null)
                    },
                    modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(label = { Text(text = "联系作者") },
                    selected = 1 == selectItem.value,
                    onClick = {
                        val url = Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=2116996207")
                        context.startActivity(Intent(Intent.ACTION_VIEW, url))
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    shape = CircleShape,
                    icon = {
                        Icon(imageVector = Icons.TwoTone.Call, contentDescription = null)
                    },
                    modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(label = { Text(text = "软件反馈") },
                    selected = 1 == selectItem.value,
                    onClick = {
                        navController.navigate("WebScreen/help") {
                            this.popUpTo("MainScreen") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    shape = CircleShape,
                    icon = {
                        Icon(imageVector = Icons.TwoTone.Help, contentDescription = null)
                    },
                    modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(label = { Text(text = "关于软件") },
                    selected = 2 == selectItem.value,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("AboutScreen")
                        }
                    },
                    shape = CircleShape,
                    icon = {
                        Icon(imageVector = Icons.TwoTone.Info, contentDescription = null)
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }, drawerState = drawerState
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "反应堆"
                )
            }, Modifier.fillMaxWidth(), navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(imageVector = Icons.TwoTone.Menu, contentDescription = null)
                }
            })
        }) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it),
            ) {
                FlowRow(
                    mainAxisSpacing = 8.dp
                ) {
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/workcenter")
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.BusinessCenter, contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "办事大厅")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/teacher")
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.SettingsSystemDaydream, contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "教务系统")
                    }
                    OutlinedButton(onClick = {
                        //toggle.value=true
                        navController.navigate("WebScreen/class")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Help, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "第二课堂")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/epidemic")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Dock, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "疫情填报")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("BinaryScreen")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Transform, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "进制转换")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/icpc")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Code, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "ICPC榜单")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/oiwiki")
                    }) {
                        Icon(imageVector = Icons.TwoTone.OilBarrel, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "OI Wiki")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/dev")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Co2, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "开发者搜索")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/mdui")
                    }) {
                        Icon(imageVector = Icons.TwoTone.AddChart, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "mdui文档")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/ctfwiki")
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.CalendarToday, contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "CTF Wiki")
                    }

                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/lan")
                    }) {
                        Icon(imageVector = Icons.TwoTone.WebStories, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "我的小窝")
                    }
                    OutlinedButton(onClick = {
                        AGConnectCrash.getInstance().testIt(context)
                    }) {
                        Icon(imageVector = Icons.TwoTone.MinorCrash, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "测试崩溃")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/aibot")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Android, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "AI机器人")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/translate")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Translate, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Google翻译")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/microsoft")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Microwave, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "WinUI文档")
                    }
                    OutlinedButton(onClick = {
                        //toggle.value=true
                        navController.navigate("WebScreen/insole")
                    }) {
                        Icon(imageVector = Icons.TwoTone.Blender, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "扫雷")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("CodePage")
                    }) {
                        Icon(imageVector = Icons.TwoTone.CodeOff, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "代码空间")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/acmer")
                    }) {
                        Icon(imageVector = Icons.TwoTone.AcUnit, contentDescription = null)
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Acmer")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WebScreen/cf")
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.CurrencyExchange, contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Codeforces年度报告")
                    }
                    OutlinedButton(onClick = {
                        navController.navigate("WifiScreen")
                    }) {
                        Icon(
                            imageVector = Icons.TwoTone.Wifi, contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Wifi密码查看(需root)")
                    }
                }
            }
        }
    }
}
