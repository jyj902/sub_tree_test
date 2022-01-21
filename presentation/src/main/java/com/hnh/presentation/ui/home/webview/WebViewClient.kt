package com.hnh.presentation.ui.home.webview

import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import java.net.URISyntaxException

class WebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (request!!.url != null) {
            var uri = request!!.url.toString()
            when {
                uri.startsWith("intent://") -> {
                    try {
                        val intent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME)
                        if (intent != null) {

                        }
                        return true
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }
}