package com.example.jing

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jing.components.MyIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Translatebar()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "关于软件")
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
        Column(
            modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Image(painter = painterResource(id = R.drawable.about),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clickable {
                    }
                )
            }
            Surface(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logo), contentDescription = null,
                        tint = Color(0xFF03A9F4)
                    )
                }
            }
            Text(
                text = "反应堆",
                fontWeight = FontWeight.Black,
                fontSize = 40.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(text = "v0.1-alpha01")
            Column(Modifier.weight(1f)) {
                Spacer(modifier = Modifier.weight(0.4f))
                Text(modifier = Modifier.padding(16.dp),text = "技术支持:",
                fontSize = 20.sp,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black
                    )
                Surface(
                    modifier = Modifier
                        .padding(paddingValues = PaddingValues(16.dp, 16.dp, 16.dp, 0.dp))
                        .clickable {
                            navController.navigate("WebScreen/as")
                        }
                        .fillMaxWidth()
                        .weight(0.3f)
                        .clip(RoundedCornerShape(50.dp))
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                    verticalAlignment = CenterVertically
                        ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(id = R.drawable.androidstudio),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .padding(4.dp)
                            )
                            Text(text = "AndroidStudio", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
                            Spacer(modifier = Modifier.weight(1f))
                            MyIconButton(onClick = {
                                navController.navigate("WebScreen/as")
                            }) {
                                Icon(painter = painterResource(id = R.drawable.twotone_launch_24), contentDescription =null )
                            }
                        }
                    }
                }
                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    modifier = Modifier.padding(16.dp)
                )
                    Surface(
                        modifier = Modifier
                            .padding(paddingValues = PaddingValues(16.dp, 0.dp, 16.dp, 16.dp))
                            .clickable {
                                navController.navigate("WebScreen/compose")
                            }
                            .fillMaxWidth()
                            .weight(0.3f)
                            .clip(RoundedCornerShape(50.dp))
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                            verticalAlignment = CenterVertically
                            ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Image(
                                    painter = painterResource(id = R.drawable.compose),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .padding(4.dp)
                                )
                                Text(text = "Jetpack Compose", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
                                Spacer(modifier = Modifier.weight(1f))
                                MyIconButton(onClick = {
                                    navController.navigate("WebScreen/compose")
                                }) {
                                    Icon(painter = painterResource(id = R.drawable.twotone_launch_24), contentDescription =null )
                                }
                            }
                        }
                    }
                }
        }
        BackHandler(true) {
            navController.navigateUp()
        }
    }
}