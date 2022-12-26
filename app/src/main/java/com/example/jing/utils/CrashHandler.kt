package com.example.jing.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.os.Build.VERSION
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.jing.activity.CodeActivity
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date

class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {
    private var mContext: Context? = null
    private val info: MutableMap<String, String> = HashMap()
    fun init(context: Context) {
        mContext = context.applicationContext
        collectDeviceInfo(mContext)
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        saveCrashInfo(thread.name, ex)
        // Save the world, hopefully
        if (Looper.myLooper() != null) {
            val handler = Handler(Looper.myLooper()!!)
            while (true) {
                try {
                    handler.post {
                        /*Toast.makeText(
                            mContext,
                            R.string.err_crash_loop,
                            Toast.LENGTH_SHORT
                        ).show()*/
                        val intent=Intent(mContext, CodeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("flag",1)
                        mContext?.startActivity(intent)
                    }
                    Looper.loop()
                } catch (t: Throwable) {
                    saveCrashInfo(thread.name, t)
                }
            }
        }
    }

    fun collectDeviceInfo(ctx: Context?) {
        try {
            val pm = ctx!!.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                info["versionName"] = versionName
                info["versionCode"] = versionCode
            }
        } catch (e: NameNotFoundException) {
            Log.e(LOG_TAG, "an error occurred while collecting package info", e)
        }
        var fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                val obj = field[null]
                if (obj is Array<*>) {
                    info[field.name] = Arrays.toString(obj as Array<String?>)
                } else {
                    info[field.name] = obj.toString()
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "an error occurred while collecting crash info", e)
            }
        }
        fields = VERSION::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                val obj = field[null]
                if (obj is Array<*>) {
                    info[field.name] = Arrays.toString(obj as Array<String?>)
                } else {
                    if (obj != null) {
                        info[field.name] = obj.toString()
                    }
                }
            } catch (e: Exception) {
                Log.e(LOG_TAG, "an error occurred while collecting crash info", e)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun saveCrashInfo(threadName: String, ex: Throwable) {
        val sb = StringBuilder()
        val timestamp = System.currentTimeMillis()
        sb.append("Crash at ").append(timestamp).append("(timestamp) in thread named '")
            .append(threadName).append("'\n")
        sb.append("Local date and time:")
            .append(SimpleDateFormat.getDateTimeInstance().format(Date(timestamp))).append('\n')
        for ((key, value) in info) {
            sb.append(key).append("=").append(value).append("\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result).append('\n')
        try {
            Log.e(LOG_TAG, sb.toString())
            val fos1: FileOutputStream? =
                mContext!!.openFileOutput("crash-journal.log", AppCompatActivity.MODE_PRIVATE)
            if (fos1 != null) {
                fos1.close()
            }
            val fos = mContext!!.openFileOutput("crash-journal.log", Context.MODE_APPEND)
            fos.write(sb.toString().toByteArray())
            fos.close()
        } catch (e: Exception) {
            Log.e(LOG_TAG, "an error occurred while writing file...", e)
        }
    }

    companion object {
        const val LOG_TAG = "CrashHandler"

        @SuppressLint("StaticFieldLeak")
        val INSTANCE = CrashHandler()
    }
}