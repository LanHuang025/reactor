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
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.GrammarRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.dsl.languages
import io.github.rosemoe.sora.langs.textmate.registry.model.ThemeModel
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.SymbolInputView
import org.eclipse.tm4e.core.registry.IThemeSource

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
                    inputView.bindEditor(editor)
                    val language = TextMateLanguage.create(
                        "source.java", true
                    )
                    editor.setEditorLanguage(language)
                }
            },
            update = {
            }
        )
    }
}
private /*suspend*/ fun loadDefaultThemes(context: Context) /*= withContext(Dispatchers.IO)*/ {

    //add assets file provider
    FileProviderRegistry.getInstance().addFileProvider(
        AssetsFileResolver(
            context.assets
        )
    )


    val themes = arrayOf("darcula", "abyss", "quietlight", "solarized_drak")
    val themeRegistry = ThemeRegistry.getInstance()
    themes.forEach { name ->
        val path = "textmate/$name.json"
        themeRegistry.loadTheme(
            ThemeModel(
                IThemeSource.fromInputStream(
                    FileProviderRegistry.getInstance().tryGetInputStream(path), path, null
                ), name
            )
        )
    }

    themeRegistry.setTheme("quietlight")
}
private fun loadDefaultLanguagesWithDSL() {
    GrammarRegistry.getInstance().loadGrammars(
        languages {
            language("java") {
                grammar = "textmate/java/syntaxes/java.tmLanguage.json"
                defaultScopeName()
                languageConfiguration = "textmate/java/language-configuration.json"
            }
            language("kotlin") {
                grammar = "textmate/kotlin/syntaxes/Kotlin.tmLanguage"
                defaultScopeName()
                languageConfiguration = "textmate/kotlin/language-configuration.json"
            }
            language("python") {
                grammar = "textmate/python/syntaxes/python.tmLanguage.json"
                defaultScopeName()
                languageConfiguration = "textmate/python/language-configuration.json"
            }
        }
    )
}
