package com.example.test.networkDemo

import android.util.Log
import com.google.android.material.tabs.TabLayout.TabGravity
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class ContentHandler : DefaultHandler() {
    private val TAG = "ContentHandler"
    private var nodeName = ""
    private lateinit var id: StringBuilder
    private lateinit var name: StringBuilder
    private lateinit var version: StringBuilder
    override fun startDocument() {
        id = StringBuilder()
        name = StringBuilder()
        name = StringBuilder()
    }

    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
//        記錄當前節點名
        if (localName != null) {
            nodeName = localName
        }
        Log.d(TAG, "uri is $uri")
        Log.d(TAG, "localName is $localName")
        Log.d(TAG, "qName is $qName")
        Log.d(TAG, "attributes is $attributes")
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
//        根據當前節點名判斷將內容添加到哪一個StringBuilder對象中
        when (nodeName) {
            "id" -> id.append(ch, start, length)
            "name" -> name.append(ch, start, length)
            "version" -> version.append(ch, start, length)
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if ("app" == localName) {
            Log.d(TAG, "id is ${id.toString().trim()}")
            Log.d(TAG, "name is ${name.toString().trim()}")
            Log.d(TAG, "version is ${version.toString().trim()}")
//            最後將StringBuilder清空
            id.setLength(0)
            name.setLength(0)
            version.setLength(0)
        }
    }

    override fun endDocument() {
        super.endDocument()
    }

}