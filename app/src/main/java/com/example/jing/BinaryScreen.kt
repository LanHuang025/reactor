package com.example.jing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
@ExperimentalMaterial3Api
fun BinaryScreen(navController: NavController){
    Translatebar()
    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "进制转换")
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
        Surface(modifier = Modifier.padding(it)) {
            Column {
                var text1 by rememberSaveable { mutableStateOf("") }
                var text2 by rememberSaveable { mutableStateOf("") }
                var text3 by rememberSaveable { mutableStateOf("") }
                var text4 by rememberSaveable { mutableStateOf("") }
                OutlinedCard(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = text1,
                        onValueChange = { text1 = it },
                        label = { Text("二进制") },
                        trailingIcon = {
                            IconButton(onClick = { text1=""}) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )

                }
                OutlinedCard(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = text2,
                        onValueChange = { text2 = it },
                        label = { Text("八进制") },
                        trailingIcon = {
                            IconButton(onClick = { text2=""}) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )

                }
                OutlinedCard(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = text3,
                        onValueChange = { text3 = it },
                        label = { Text("十进制") },
                        trailingIcon = {
                            IconButton(onClick = { text3=""}) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )
                }
                OutlinedCard(Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = text4,
                        onValueChange = { text4 = it },
                        label = { Text("十六进制") },
                        trailingIcon = {
                            IconButton(onClick = { text4=""}) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )

                }
            }
        }
    }

}