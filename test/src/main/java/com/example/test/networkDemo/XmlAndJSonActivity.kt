package com.example.test.networkDemo

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread

class XmlAndJSonActivity : AppCompatActivity() {
    private val TAG = "XmlAndJSonActivity"
    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
//                    .url("http://192.168.8.148:8081/get_data.xml")
                    .url("http://192.168.8.148:8081/get_data.json")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
//                    parseXMLWithPull(responseData)
//                    parseXMLWithSAX(responseData)
//                    parseJSONWithHSONObject(responseData)
                    parseJSONWithGson(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 功能描述：xml的pull解析方式
     * @param [xmlData]
     * @author Tzy
     * @date 2023/11/16 10:51
     */
    private fun parseXMLWithPull(xmlData: String) {
        try {
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlData))
            var eventType = xmlPullParser.eventType
            var id = ""
            var name = ""
            var version = ""
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (nodeName) {
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if ("app" == nodeName) {
                            Log.d("MainActivity", "id is $id")
                            Log.d("MainActivity", "name is $name")
                            Log.d("MainActivity", "version is $version")
                        }
                    }
                }
                eventType = xmlPullParser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 功能描述：SAX的xml解析方式
     * @param [xmlData]
     * @author Tzy
     * @date 2023/11/16 11:15
     */
    private fun parseXMLWithSAX(xmlData: String) {
        try {
            val factory = SAXParserFactory.newInstance()
            val xmlReader = factory.newSAXParser().xmlReader
            val handler = ContentHandler()
//            將ContentHandler的實例設置到XMLReader中
            xmlReader.contentHandler = handler
//            開始執行刷新
            xmlReader.parse(InputSource(StringReader(xmlData)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 功能描述：使用官方提供的JsonObject獲取json數據
     * @param [jsonData]
     * @author Tzy
     * @date 2023/11/16 11:23
     */
    private fun parseJSONWithHSONObject(jsonData: String) {
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")
                Log.d(TAG,"id is $id")
                Log.d(TAG,"id is $name")
                Log.d(TAG,"id is $version")
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    /**
     * 功能描述：通過第三方Gson可以將json轉換成對象的形式，如果需要解析的是一段數組，那麼需要進行typeOf的類型轉換
     * @param [jsonData]
     * @author Tzy
     * @date 2023/11/16 11:32
     */
    private fun parseJSONWithGson(jsonData:String){
        val gson= Gson()
        val typeOf=object : TypeToken<List<App>>() {}.type
        val appList=gson.fromJson<List<App>>(jsonData,typeOf)
        for (app in appList) {
            Log.d(TAG,"id is ${app.id}")
            Log.d(TAG,"name is ${app.name}")
            Log.d(TAG,"version is ${app.version}")
        }
    }
}