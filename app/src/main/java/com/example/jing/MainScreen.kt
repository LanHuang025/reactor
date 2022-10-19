package com.example.jing

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Call
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Help
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Menu
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, context:Context) {
    val systemUiController = rememberSystemUiController()
    val usedarkIcon = !isSystemInDarkTheme()
    val backFlag= remember {
        mutableStateOf(true)
    }
    LaunchedEffect(systemUiController, usedarkIcon) {
        systemUiController.setSystemBarsColor(
            Color.Transparent,
            darkIcons = usedarkIcon
        )
        with(systemUiController) {
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectItem = remember {
        mutableStateOf(0)
    }
    ModalNavigationDrawer(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(), drawerContent = {
            ModalDrawerSheet {
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
                        navController.navigate("WebScreen/help")
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
                    Modifier
                        .fillMaxWidth(),
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
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it)
            ) {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.TwoTone.Favorite, contentDescription = null)
                    Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                    Text(text = "按钮")
                }
            }
        }
    }
}
