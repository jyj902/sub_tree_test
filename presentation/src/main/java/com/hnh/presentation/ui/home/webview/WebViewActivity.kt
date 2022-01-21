package com.hnh.presentation.ui.home.webview

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.hnh.presentation.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import android.webkit.WebViewClient
import com.hnh.presentation.util.PreferenceUtil
import com.hnh.presentation.util.URL
import java.net.URISyntaxException


@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initWebView()
    }

    private fun init() {
        setSupportActionBar(binding.topAppBar)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initWebView() {
        binding.hnhWebview.apply {
            webViewClient = webViewClientKakao

            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
        }

        val userUid = PreferenceUtil.preferenceInstance(this).userUid!!
        binding.hnhWebview.loadUrl(URL.SKELETON + userUid)
    }

    override fun onBackPressed() {
        if (binding.hnhWebview.canGoBack()) {
            binding.hnhWebview.goBack()
        } else {
            finish()
            super.onBackPressed()
        }
    }

    //TODO:WebViewClient.kt 으로 코드 이동 필요
    private var webViewClientKakao = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request!!.url != null) {
                var uri = request!!.url.toString()
                when {
                    uri.startsWith("intent:kakaolink://") -> {
                        try {
                            val intent = Intent.parseUri(uri, Intent.URI_INTENT_SCHEME)
                            var uri = Uri.parse(intent.dataString)

                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                            return true
                        } catch (e: URISyntaxException) {
                            e.printStackTrace()
                            return false
                        } catch (e: ActivityNotFoundException) {
                            if (intent == null) {
                                return false
                            }
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=com.kakao.talk")
                                )
                            )
                            return true
                        }
                    }
                }
            }

            return super.shouldOverrideUrlLoading(view, request)
        }
    }
}
