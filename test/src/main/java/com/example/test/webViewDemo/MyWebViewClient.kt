package com.example.test.webViewDemo

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity

class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (Uri.parse(url).host == "https://www.baidu.com") {
            return false
        }
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            view?.context?.startActivity(this)
        }
        return true
    }
}