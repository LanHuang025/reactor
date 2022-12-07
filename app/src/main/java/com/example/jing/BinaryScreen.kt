package com.example.jing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material.icons.twotone.Clear
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.math.BigInteger

@Composable
@ExperimentalMaterial3Api
fun BinaryScreen(navController: NavController) {
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
    ) { it ->
        Surface(modifier = Modifier.padding(it)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState(), true)) {
                var text1 by rememberSaveable { mutableStateOf("") }
                var text2 by rememberSaveable { mutableStateOf("") }
                var text3 by rememberSaveable { mutableStateOf("") }
                var text4 by rememberSaveable { mutableStateOf("") }
                var flag by rememberSaveable {
                    mutableStateOf(0)
                }
                var warn by remember {
                    mutableStateOf(false)
                }
                if (warn) {
                    AlertDialog(onDismissRequest = {

                    },
                        icon = {
                            Icon(
                                imageVector = Icons.TwoTone.Warning,
                                contentDescription = null
                            )
                        },
                        title = {
                            Text(text = "输入格式错误")
                        },
                        text = {
                            Text(text = "请输入格式正确的数字")
                        },
                        confirmButton = {
                            TextButton(onClick = { warn = false }, Modifier.fillMaxWidth()) {
                                Text(text = "知道了")
                            }
                        }
                    )
                }
                OutlinedCard(Modifier.padding(16.dp)) {
                    var data = ""
                    if (flag == 0) {
                        data = text3
                    } else if (flag == 1) {
                        if (text1 == "") data = "" else data = BigInteger(text1, 2).toString()
                    } else if (flag == 2) {
                        if (text2 == "") data = "" else data = BigInteger(text2, 8).toString()
                    } else if (flag == 3) {
                        data = text3
                    } else if (flag == 4) {
                        if (text4 == "") data = "" else data = BigInteger(text4, 16).toString()
                    }
                    OutlinedTextField(
                        modifier = Modifier.onFocusChanged {
                            if (it.isFocused) {
                                flag = 3;
                            }
                        },
                        value = data,
                        onValueChange = { text3 = it },
                        label = { Text("十进制") },
                        trailingIcon = {
                            IconButton(onClick = { text3 = "" }) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        },
                    )
                }
                OutlinedCard(Modifier.padding(16.dp)) {
                    var data = ""
                    if (flag == 0) {
                        data = text1
                    } else if (flag == 1) {
                        data = text1
                    } else if (flag == 2) {
                        if (text2 == "") data = "" else data =
                            BigInteger(text2.replace(" ", ""), 8).toString(2)
                    } else if (flag == 3) {
                        if (text3 == "" || text3 == "-") data = "" else data =
                            BigInteger(text3.replace(" ", ""), 10).toString(2)
                    } else if (flag == 4) {
                        if (text4 == "") data = "" else data =
                            BigInteger(text4.replace(" ", ""), 16).toString(2)
                    }
                    OutlinedTextField(
                        modifier = Modifier.onFocusChanged {
                            if (it.isFocused) {
                                flag = 1;
                            }
                        },
                        value = data,
                        onValueChange = {
                            if (flag==1&&it.isNotEmpty()){
                                if (it[0]-'0' !in 0..1){
                                    text1=""
                                    warn=true
                                }else {
                                    text1=it
                                }
                            }
                        },
                        label = { Text("二进制") },
                        trailingIcon = {
                            IconButton(onClick = { text1 = "" }) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )

                }
                /*OutlinedCard(Modifier.padding(16.dp)) {
                    var data = ""
                    if (flag == 0) {
                        data = text2
                    } else if (flag == 1) {
                        if (text1 == "") data = "" else data =
                            BigInteger(text1.replace(" ", ""), 2).toString(8)
                    } else if (flag == 2) {
                        data = text2
                    } else if (flag == 3) {
                        if (text3 == "" || text3 == "-") data = "" else data =
                            BigInteger(text3.replace(" ", ""), 10).toString(8)
                    } else if (flag == 4) {
                        if (text4 == "") data = "" else data =
                            BigInteger(text4.replace(" ", ""), 16).toString(8)
                    }
                    OutlinedTextField(
                        modifier = Modifier.onFocusChanged {
                            if (it.isFocused) {
                                flag = 2;
                            }
                        },
                        value = data,
                        onValueChange = {
                            if (flag==2){
                                if (it[it.length - 1] - '0' in 1..2)
                                    text2 = it else if (it.length==1) {
                                    text2 = ""
                                    warn=true
                                } else {
                                    //it.removeRange(it.length - 1, it.length)
                                    warn=true
                                }
                            }else {
                                text2=it
                            }
                        },
                        label = { Text("八进制") },
                        trailingIcon = {
                            IconButton(onClick = { text2 = "" }) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )

                }
                OutlinedCard(Modifier.padding(16.dp)) {
                    var data = ""
                    if (flag == 0) {
                        data = text4
                    } else if (flag == 1) {
                        if (text1 == "") data = "" else data =
                            BigInteger(text1.replace(" ", ""), 2).toString(16)
                    } else if (flag == 2) {
                        if (text2 == "") data = "" else data =
                            BigInteger(text2.replace(" ", ""), 8).toString(16)
                    } else if (flag == 3) {
                        if (text3 == "" || text3 == "-") data = "" else data =
                            BigInteger(text3.replace(" ", ""), 10).toString(16)
                    } else if (flag == 4) {
                        data = text4
                    }
                    OutlinedTextField(
                        modifier = Modifier.onFocusChanged {
                            if (it.isFocused) {
                                flag = 4;
                            }
                        },
                        value = data,
                        onValueChange = {
                            if (flag==4){
                                if (it[it.length - 1] - '0' in 1..2)
                                    text4 = it else if (it.length==1) {
                                    text4 = ""
                                    warn=true
                                } else {
                                    //it.removeRange(it.length - 1, it.length)
                                    warn=true
                                }
                            }else {
                                text4=it
                            }
                        },
                        label = { Text("十六进制") },
                        trailingIcon = {
                            IconButton(onClick = { text4 = "" }) {
                                Icon(imageVector = Icons.TwoTone.Clear, contentDescription = null)
                            }
                        }
                    )

                }*/
            }
        }
    }

}