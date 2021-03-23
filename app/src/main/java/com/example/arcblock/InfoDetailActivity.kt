package com.example.arcblock

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info_detail.*

/**
 * 信息底层页
 *
 * Created by Rossi on 3/22/21.
 */
class InfoDetailActivity : AppCompatActivity() {

    private val TAG = "InfoListActivity"

    private var path: String? = ""

    var hideJs = "var hideScript = document.createElement(\"script\"); " +
            "document.getElementById('page-wrapper').style.display = 'none';" +
            "document.getElementsByClassName('detail__Div-sc-19x3b7e-1')[0].getElementsByClassName('MuiButtonBase-root')[0].style.display = 'none';" +
            "document.getElementsByClassName('detail__Div-sc-19x3b7e-1')[0].getElementsByClassName('MuiButtonBase-root')[1].style.display = 'none';" +
            "document.getElementsByClassName('footer__OuterDiv-sc-12buhtt-0')[0].style.display = 'none';" +
            "document.getElementsByClassName('MuiGrid-spacing-xs-5')[0].style.width = '100%';" +
            "document.getElementsByClassName('MuiGrid-spacing-xs-5')[0].style.margin = '0px';"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detail)

        path = intent.getStringExtra("path")

        val url = Api.BaseUrl + path
        Log.e(TAG, url)

        srl_webview.isRefreshing = true
        initWebview()
        webview.loadUrl(url)
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.e(TAG, "onPageStarted")
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                Log.e(TAG, "shouldOverrideUrlLoading url = $url")
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.e(TAG, "onPageFinished")
                view?.evaluateJavascript("javascript:$hideJs") {
                    Handler().postDelayed({
                        webview.visibility = View.VISIBLE
                        srl_webview.isRefreshing = false
                    }, 1200)
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                handler.proceed()
            }
        }

        cv_back.setOnClickListener { onBackPressed() }
    }

    fun initWebview() {
        val webSettings = webview.settings
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.javaScriptEnabled = true
        webSettings.builtInZoomControls = false
        webSettings.domStorageEnabled = true
        webSettings.setSupportZoom(false)
        webSettings.displayZoomControls = false
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
    }

    override fun onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}