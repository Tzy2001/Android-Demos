package com.example.test.webViewDemo

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils

class WebAppInterface() {
    @JavascriptInterface
    fun showToast(toast: String) {
        ToastUtils.showShort(toast)
    }
}