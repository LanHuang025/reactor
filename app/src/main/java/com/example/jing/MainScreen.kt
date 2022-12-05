package com.example.jing

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddChart
import androidx.compose.material.icons.twotone.CalendarToday
import androidx.compose.material.icons.twotone.Call
import androidx.compose.material.icons.twotone.Co2
import androidx.compose.material.icons.twotone.Code
import androidx.compose.material.icons.twotone.CurrencyExchange
import androidx.compose.material.icons.twotone.Dock
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Help
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material.icons.twotone.OilBarrel
import androidx.compose.material.icons.twotone.Transform
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
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, context:Context) {
    Translatebar()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectItem = remember {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize(), drawerContent = {
            ModalDrawerSheet(windowInsets = WindowInsets(0,0,0,0)) {
                Image(
                    painter = painterResource(id = R.drawable.background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .height(200.dp)
                )
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = "主页") },
                    selected = 0 == selectItem.value,
                    onClick = {
                        scope.launch {
                            if (selectItem.value == 0) {
                                drawerState.close()
                            } else {
                                selectItem.value = 0
                            }
                        }
                    }, shape = CircleShape, icon = {
                        Icon(imageVector = Icons.TwoTone.Home, contentDescription = null)
                    }, modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = "联系作者") },
                    selected = 1 == selectItem.value,
                    onClick = {
                            val url= Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=2116996207")
                            context.startActivity(Intent(Intent.ACTION_VIEW,url))
                        scope.launch {
                            drawerState.close()
                        }
                    }, shape = CircleShape, icon = {
                        Icon(imageVector = Icons.TwoTone.Call, contentDescription = null)
                    }, modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = "软件反馈") },
                    selected = 1 == selectItem.value,
                    onClick = {
                        navController.navigate("WebScreen/help"){
                            this.popUpTo("MainScreen"){
                                saveState=true
                            }
                            launchSingleTop=true
                            restoreState=true
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    }, shape = CircleShape, icon = {
                        Icon(imageVector = Icons.TwoTone.Help, contentDescription = null)
                    }, modifier = Modifier.padding(8.dp)
                )
                NavigationDrawerItem(
                    label = { Text(text = "关于软件") },
                    selected = 2 == selectItem.value,
                    onClick = {
                        scope.launch{
                            drawerState.close()
                            navController.navigate("AboutScreen")
                        }
                    }, shape = CircleShape, icon = {
                        Icon(imageVector = Icons.TwoTone.Info, contentDescription = null)
                    }, modifier = Modifier.padding(8.dp)
                )
            }
        }, drawerState = drawerState
    ) {
        Scaffold(modifier = Modifier
            .fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text =
                        "反应堆"
                    )
                },
                    Modifier.fillMaxWidth(),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.TwoTone.Menu, contentDescription = null)
                        }
                    }
                )
            }
        ) {
                FlowColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues = it),
                    mainAxisSize = SizeMode.Expand
                    ) {
                    FlowRow(
                        mainAxisAlignment = MainAxisAlignment.SpaceBetween,mainAxisSpacing = 8.dp,
                        mainAxisSize = SizeMode.Expand
                    ) {
                        OutlinedButton(onClick = {
                            //toggle.value=true
                            navController.navigate("WebScreen/insole")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "扫雷")
                        }
                        OutlinedButton(onClick = {
                            //toggle.value=true
                            navController.navigate("WebScreen/class")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.Help, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "第二课堂")
                        }
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/epidemic")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.Dock, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "疫情填报")
                        }
                }
                    FlowRow(
                        mainAxisAlignment = MainAxisAlignment.SpaceBetween,mainAxisSpacing = 8.dp,
                        mainAxisSize = SizeMode.Expand
                    ) {
                        OutlinedButton(onClick = {
                            navController.navigate("BinaryScreen")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.Transform, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "进制转换")
                        }
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/icpc")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.Code, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "ICPC榜单")
                        }
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/oiwiki")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.OilBarrel, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "OI Wiki")
                        }
                    }
                    FlowRow(
                        mainAxisAlignment = MainAxisAlignment.SpaceBetween,mainAxisSpacing = 8.dp,
                        mainAxisSize = SizeMode.Expand
                    ) {
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/dev")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.Co2, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "开发者搜索")
                        }
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/mdui")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.AddChart, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "mdui文档")
                        }
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/ctfwiki")
                        }
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.CalendarToday,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "CTF Wiki")
                        }
                    }
                    FlowRow(
                        mainAxisAlignment = MainAxisAlignment.SpaceBetween,mainAxisSpacing = 8.dp,
                        mainAxisSize = SizeMode.Expand
                    ) {
                        OutlinedButton(onClick = {
                            navController.navigate("WifiScreen")
                        }
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Wifi,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "Wifi密码查看(需root)")
                        }
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/cf")
                        }
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.CurrencyExchange,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "Codeforces年度报告")
                        }
                    }
                    FlowRow(mainAxisAlignment = MainAxisAlignment.SpaceBetween,mainAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth(),
                        mainAxisSize = SizeMode.Expand
                        ) {
                        OutlinedButton(onClick = {
                            context.startActivity(Intent(context, CodeActivity::class.java))
                        }
                        ) {
                            Image(painter = painterResource(id = R.drawable.c), contentDescription = null,Modifier.size(50.dp))
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "c语言编辑器")
                        }
                        OutlinedButton(onClick = {

                        }
                        ) {
                            Image(painter = painterResource(id = R.drawable.python), contentDescription = null,Modifier.size(50.dp))
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "python编辑器")
                        }
                    }
                    FlowRow(mainAxisAlignment = MainAxisAlignment.SpaceBetween,mainAxisSpacing = 8.dp,
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisSize = SizeMode.Expand
                    ) {
                        OutlinedButton(onClick = {
                            navController.navigate("WebScreen/lan")
                        }
                        ) {
                            Icon(imageVector = Icons.TwoTone.WebStories, contentDescription = null)
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "我的小窝")
                        }
                    }
                }
        }
    }
}
