package com.example.jing.activity

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.jing.MainPage
import com.example.jing.ui.theme.MyTheme
import com.example.jing.utils.CrashHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.huawei.agconnect.AGConnectInstance
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.agconnect.appmessaging.AGConnectAppMessaging
import com.huawei.agconnect.common.network.AccessNetworkManager
import com.huawei.hms.mlsdk.common.MLApplication
import java.io.FileNotFoundException
import java.io.IOException

 class MainActivity : ComponentActivity() {
    var context=this
    private val TAKE_PHOTO = 1 //拍照
    private val GET_PHOTO = 2 //取照片
    private var bitmap:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window,false)
        super.onCreate(savedInstanceState)
        CrashHandler.INSTANCE.init(this)
        try {
            val builder = AGConnectOptionsBuilder()
            builder.setClientId("1042731718856953664")
            builder.setClientSecret("3006D5A7188EB1EC013F224B9D67F6F8D9C0FD30BF3686ED3EC9215EA2C78993")
            builder.setApiKey("DAEDAEYGFUKlIgiF9Efam4Nlc8MGuSnMfHV7CunUCMVC0cscvUHqwZJYOrTdAvq/nUunPDOAZnc4ThmNQKmLWgyPOzSYJtyJIIfnbA==")
            builder.setCPId("30086000585186337")
            builder.setProductId("99536292102830512")
            builder.setAppId("107473021")
            AGConnectInstance.initialize(this, builder)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        AccessNetworkManager.getInstance().setAccessNetwork(true)
        AGConnectAppMessaging.getInstance().isFetchMessageEnable = true
        val appMessaging: AGConnectAppMessaging? = AGConnectAppMessaging.getInstance()
        appMessaging?.setForceFetch("AppLaunch")
        appMessaging?.isDisplayEnable = false
        MLApplication.getInstance().setUserRegion(MLApplication.REGION_DR_CHINA)
        MLApplication.getInstance().apiKey = "DAEDAEYGFUKlIgiF9Efam4Nlc8MGuSnMfHV7CunUCMVC0cscvUHqwZJYOrTdAvq/nUunPDOAZnc4ThmNQKmLWgyPOzSYJtyJIIfnbA=="
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        setContent {
            MyTheme {
                Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                    MainPage(this,clipboard)
                }
            }
        }
        val intent =Intent(Intent.ACTION_GET_CONTENT);
        intent.type = "image/*";
        //startActivityForResult(intent, GET_PHOTO);

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {  //回传值接受成功

            if (requestCode == TAKE_PHOTO) {    //拍照取图

                val bundle = data?.getExtras();   //获取data数据集合
                val bitmapx = bundle?.get("data");        //获得data数据
                bitmap= bitmapx as Bitmap?

            }

            if (requestCode == GET_PHOTO) {     //相册取图

                val contentResolver = contentResolver;
                try {

                    val bitmapx = BitmapFactory.decodeStream(
                        data?.data
                        ?.let { contentResolver.openInputStream(it) });
                    bitmap= bitmapx
                    Log.e("TAG", "从相册回传bitmap：$bitmapx");
                    val view=ImageView(this)
                    view.setImageBitmap(bitmap)
                    MaterialAlertDialogBuilder(this).setTitle("图片显示")
                        .setView(view)
                        .create().show()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace();
                }
            }

        }

    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        newConfig.let { super.onConfigurationChanged(it) }
    }
}