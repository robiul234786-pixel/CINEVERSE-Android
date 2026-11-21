package com.cineverse.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

// Cloudflare Pages-এ আপলোড করা সাইটের লিংক এখানে বসাও (যেমন "https://cineverse.pages.dev").
// ফাঁকা রাখলে অ্যাপের ভেতরের কপি (assets/index.html) চলবে।
private const val APP_URL = ""

class MainActivity : Activity() {
    private lateinit var web: WebView
    private var customView: View? = null
    private lateinit var root: FrameLayout

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#090A0E")
        window.navigationBarColor = Color.parseColor("#171922")

        root = FrameLayout(this).apply { setBackgroundColor(Color.parseColor("#090A0E")) }
        web = WebView(this)
        root.addView(web, FrameLayout.LayoutParams(-1, -1))
        setContentView(root)
        root.setOnApplyWindowInsetsListener { v, insets ->
            v.setPadding(0, insets.systemWindowInsetTop, 0, 0)
            insets
        }

        web.setBackgroundColor(Color.parseColor("#090A0E"))
        web.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            mediaPlaybackRequiresUserGesture = false
            mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
            allowFileAccess = true
        }
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(v: WebView, r: WebResourceRequest): Boolean {
                val u = r.url
                val host = u.host ?: ""
                val ok = u.scheme == "file" ||
                    host.endsWith("youtube.com") || host.endsWith("youtube-nocookie.com") ||
                    host.endsWith("firebaseapp.com") || host.endsWith("gstatic.com") ||
                    (APP_URL.isNotEmpty() && APP_URL.contains(host))
                if (ok) return false
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(u.toString())))
                return true
            }
        }
        // YouTube ফুলস্ক্রিন সাপোর্ট
        web.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View, cb: CustomViewCallback) {
                customView = view
                root.addView(view, FrameLayout.LayoutParams(-1, -1))
                web.visibility = View.GONE
            }
            override fun onHideCustomView() {
                customView?.let { root.removeView(it) }
                customView = null
                web.visibility = View.VISIBLE
            }
        }

        if (savedInstanceState != null) web.restoreState(savedInstanceState)
        else web.loadUrl(if (APP_URL.isNotEmpty()) APP_URL else "file:///android_asset/index.html")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        web.saveState(outState)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        when {
            customView != null -> web.webChromeClient?.onHideCustomView()
            web.canGoBack() -> web.goBack()
            else -> super.onBackPressed()
        }
    }

    override fun onPause() { super.onPause(); web.onPause() }
    override fun onResume() { super.onResume(); web.onResume() }
}
