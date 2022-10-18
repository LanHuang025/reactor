package com.example.jing

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController){
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
        Column(modifier = Modifier.padding(it), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
            ) {
            Surface(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))) {
                Image(painter = painterResource(id = R.drawable.about)
                    , contentDescription =null, contentScale = ContentScale.Crop, modifier =Modifier.clickable {
                    }
                )
            }
            Surface(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(painter = painterResource(id = R.drawable.logo), contentDescription =null,
                        tint = Color(0xFF03A9F4))
                }
            }
            Text(text = "反应堆", fontWeight = FontWeight.Black, fontSize = 40.sp
                , modifier = Modifier.padding(4.dp)
            )
            Text(text = "v0.1-alpha01")
            Surface(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))) {
                Row(modifier = Modifier.fillMaxWidth()) {

                }
            }
            Surface(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp))) {
                Row(modifier = Modifier.fillMaxWidth()) {

                }
            }
        }
    }
    BackHandler(true) {
        navController.navigateUp()
    }
}