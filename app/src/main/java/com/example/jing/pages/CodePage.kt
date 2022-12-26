package com.example.jing.pages

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jing.R
import com.example.jing.Translatebar
import com.example.jing.activity.CodeActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodePage(navController: NavController,context:Context){
    Translatebar()
    var isShow by remember {
        mutableStateOf(false)
    }
    var message by remember {
        mutableStateOf("")
    }
        if (isShow) {
            AlertDialog(onDismissRequest = { isShow = false },
                title = {
                    Text(text = "反应堆")
                },
                text = {
                    Text(text = message)
                },
                confirmButton = {
                    TextButton(onClick = {
                        isShow = false
                    }) {
                        Text(text = "确定")
                    }
                },
            )
        }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "代码编辑器")
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
            Modifier
                .padding(it)
                .fillMaxWidth()) {
            CodeCard(
                id =R.drawable.c,
                text = "c语言编辑器",
                onClick = {
                    context.startActivity(Intent(context, CodeActivity::class.java))
                }
            )
            CodeCard(
                id =R.drawable.python,
                text = "Python编辑器",
                onClick = {
                    message="功能尚未开发，敬请期待"
                    isShow=true
                }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeCard(onClick:()->Unit,id:Int,text:String) {
    OutlinedCard(onClick =
        onClick, elevation = CardDefaults.outlinedCardElevation(5.dp),
        colors = CardDefaults.outlinedCardColors(containerColor =
        if (isSystemInDarkTheme()) Color.Black else Color.White
        ),
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column (Modifier.padding(16.dp)){
            Image(painter = painterResource(id = id), contentDescription = null,
                Modifier
                    .size(50.dp))
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                modifier = Modifier.padding(16.dp)
            )
            Text(text = text)
        }
    }
}