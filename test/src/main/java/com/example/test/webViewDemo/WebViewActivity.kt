package com.example.test.webViewDemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.test.R

class WebViewActivity : AppCompatActivity() {
    private lateinit var webview: WebView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webview = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
//        設置這個東西可以再應用程序打開網頁，並且可以控製全屏和一些其他的
//        webview.webViewClient = WebViewClient()
//        自定義Client，檢查主機地址如果不相符就返回默認的瀏覽器解析
        webview.webViewClient = MyWebViewClient()
        webview.loadUrl("https://www.baidu.com/s?wd=lol%E5%86%B3%E8%B5%9B%E4%BB%80%E4%B9%88%E6%97%B6%E5%80%99&rsv_spt=1&rsv_iqid=0xbd7c6a64001f0992&issp=1&f=3&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=15007414_12_dg&rsv_enter=1&rsv_dl=ih_1&rsv_sug3=1&rsv_sug1=1&rsv_sug7=001&rsv_sug2=1&rsv_btype=i&rsp=1&rsv_sug9=es_2_1&rsv_sug4=2361&rsv_sug=9")
//        增加一個應用於javasc調用android的接口方法，name表示JavaScript裡的方法名
        webview.addJavascriptInterface(WebAppInterface(), "Android")
    }

    //    如果存在想要訪問的曆史記錄，那麼當點擊返回時不會直接退出而是返回上一步
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


//    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_FORWARD && webview.canGoForward()) {
//            webview.goForward()
//            return true
//        }
//        return super.onKeyUp(keyCode, event)
//    }
}