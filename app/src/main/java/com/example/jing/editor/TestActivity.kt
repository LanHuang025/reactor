package com.example.jing.editor

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer
import io.github.rosemoe.sora.langs.java.JavaLanguage
import io.github.rosemoe.sora.widget.CodeEditor

class TestActivity : AppCompatActivity() {
    private lateinit var editor: CodeEditor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editor = CodeEditor(this)
        setContentView(editor)
        editor.typefaceText = Typeface.createFromAsset(assets, "Roboto-Regular.ttf")
        editor.setEditorLanguage(JavaLanguage())
        val text = "    private final PopupWindow mWindow;\r\n" +
                "    private final CodeEditor mEditor;\r\n" +
                "    private final int mFeatures;\n\r" +
                "    private final int[] mLocationBuffer = new int[2];\r" +
                "    private final EventReceiver<ScrollEvent> mScrollListener;\r\n" +
                "    private boolean mShowState;\r" +
                "    private boolean mRegisterFlag;\n" +
                "    private boolean mRegistered;\n" +
                "    private int mOffsetX, mOffsetY, mX, mY, mWidth, mHeight;"
        editor.setText(text)
        editor.diagnostics = DiagnosticsContainer().also {
            it.addDiagnostic(DiagnosticRegion(37, 50, DiagnosticRegion.SEVERITY_ERROR))
        }
        assert(text == editor.text.toString()) { "Text check failed" }
    }

    override fun onDestroy() {
        super.onDestroy()
        editor.release()
    }
}