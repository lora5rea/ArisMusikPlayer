package com.bangaris.music

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(5, 15, 40)
        window.navigationBarColor = Color.rgb(5, 15, 40)

        val webView = WebView(this).apply {
            setBackgroundColor(Color.rgb(242, 247, 255))
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            loadUrl("file:///android_asset/player.html")
        }

        setContentView(webView)
    }

    @Deprecated("Deprecated in Android")
    override fun onBackPressed() {
        super.onBackPressed()
    }
}
