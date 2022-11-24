package com.example.jing

import android.content.Context
import android.graphics.Typeface
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.SymbolInputView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeScreen(navController: NavController,context: Context){
    Translatebar()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "C语言编辑器")
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
        AndroidView(modifier = Modifier.fillMaxSize().padding(it),
            factory = {
                View.inflate(it,R.layout.activity_main,null).apply {
                    val editor=this.findViewById<CodeEditor>(R.id.editor)
                    val inputView=this.findViewById<SymbolInputView>(R.id.symbol_input)
                    val typeface = Typeface.createFromAsset(it.assets, "JetBrainsMono-Regular.ttf")
                    inputView.addSymbols(
                        arrayOf(
                            "->",
                            "{",
                            "}",
                            "(",
                            ")",
                            ",",
                            ".",
                            ";",
                            "\"",
                            "?",
                            "+",
                            "-",
                            "*",
                            "/"
                        ), arrayOf("\t", "{}", "}", "(", ")", ",", ".", ";", "\"", "?", "+", "-", "*", "/")
                    )
                    inputView.forEachButton {
                        it.typeface = typeface
                    }
                }
            },
            update = {
            }
        )
    }
}