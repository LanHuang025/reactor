package com.example.jing

import android.app.Application
import android.content.Context
import com.huawei.agconnect.AGConnectInstance
import com.huawei.agconnect.AGConnectOptionsBuilder
import com.huawei.agconnect.appmessaging.AGConnectAppMessaging
import com.huawei.agconnect.common.network.AccessNetworkManager
import com.topjohnwu.superuser.ShellUtils
import java.io.IOException


class MyApplication : Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ShellUtils.fastCmd("su")
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
        AccessNetworkManager.getInstance().setAccessNetwork(true);
        AGConnectAppMessaging.getInstance().setFetchMessageEnable(true);
        var appMessaging: AGConnectAppMessaging? = AGConnectAppMessaging.getInstance();
        appMessaging?.setDisplayEnable(true);
    }
}