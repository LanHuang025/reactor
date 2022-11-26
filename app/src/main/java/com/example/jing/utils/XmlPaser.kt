package com.example.jing.utils

import com.example.jing.data.WifiData
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
class XmlPaser {
    fun maplist(pathname:String): List<WifiData> {
        var list: NodeList? =null
        val dbf = DocumentBuilderFactory.newInstance()
        try {
            val db = dbf.newDocumentBuilder()
            val document = db.parse(File(pathname))
            list = document.getElementsByTagName("Network")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val wifilist = mutableListOf<WifiData>()
        var wifidata: WifiData
        var name = ""
        var password = ""
        var time = ""
        if (list != null) {
            for (index in 0 until list.length) {
                val node = list.item(index)
                val attr = node.childNodes.length
                for (i in 0 until attr) {
                    val nodePro = node.childNodes.item(i)
                    if (nodePro.nodeType == 1.toShort()) {
                        for (k in 0 until nodePro.childNodes.length) {
                            val nodePlus = nodePro.childNodes.item(k)
                            if (nodePlus.nodeType == 1.toShort() && (nodePlus.attributes.item(0).nodeName == "name")) {
                                val attrname = nodePlus.attributes.item(0).nodeValue
                                if (attrname == "SSID" || attrname == "PreSharedKey" || attrname == "CreationTime") {
                                    //println(nodePlus.attributes.item(0).nodeValue)
                                    if (nodePlus.hasChildNodes()) {
                                        val value = nodePlus.childNodes.item(0).nodeValue
                                        when (attrname) {
                                            "SSID"
                                                //println(value.replace("\"",""))
                                            -> name = value.replace("\"", "")
                                            "PreSharedKey" -> password = value.replace("\"","")
                                            else -> time = value
                                        }
                                    } else password = "没有密码"
                                }
                            }
                        }
                    }
                }
                wifidata = WifiData(name, password, time)
                wifilist.add(wifidata)
            }
        }
        return wifilist
    }

}