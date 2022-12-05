package com.example.jing.editor

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.eclipse.lemminx.XMLServerLauncher
import java.net.ServerSocket
import kotlin.concurrent.thread

class LspLanguageServerService : Service() {


    companion object {
        private const val TAG = "LanguageServer"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Only used in test

        thread {
            val port = intent?.getIntExtra("port", 0) ?: 0

            val socket = ServerSocket(port)

            Log.d(TAG, "Starting socket on port ${socket.localPort}")

            val socketClient = socket.accept()

            Log.d(TAG, "connected to the client on port ${socketClient.localPort}")

            runCatching {
                XMLServerLauncher.launch(
                    socketClient.getInputStream(),
                    socketClient.getOutputStream()
                ).get()
            }.onFailure {
                Log.d(TAG, "Unexpected exception is thrown in the Language Server Thread.", it)
            }

            socketClient.close()

            socket.close()


        }

        return START_STICKY
    }


}

