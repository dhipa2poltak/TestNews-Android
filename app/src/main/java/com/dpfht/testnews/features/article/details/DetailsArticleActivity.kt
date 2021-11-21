package com.dpfht.testnews.features.article.details

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dpfht.testnews.Constant
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.ActivityDetailsArticleBinding
import com.dpfht.testnews.features.base.BaseActivity

class DetailsArticleActivity : BaseActivity() {

  private lateinit var binding: ActivityDetailsArticleBinding
  private var url = ""
  private var title = ""

  @SuppressLint("SetJavaScriptEnabled")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailsArticleBinding.inflate(layoutInflater)
    setContentView(binding.root)

    url = intent.getStringExtra(Constant.KEY_EXTRA_URL) ?: ""
    if (url.isNotEmpty()) {
      binding.wvArticle.settings.javaScriptEnabled = true
      binding.wvArticle.settings.javaScriptCanOpenWindowsAutomatically = true
      binding.wvArticle.settings.builtInZoomControls = true
      binding.wvArticle.settings.setSupportZoom(true)
      binding.wvArticle.webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
          return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
          super.onPageStarted(view, url, favicon)

          prgDialog.show()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
          super.onPageFinished(view, url)

          prgDialog.dismiss()
        }
      }

      binding.wvArticle.loadUrl(url)
    }

    title = intent.getStringExtra(Constant.KEY_EXTRA_TITLE) ?: resources.getString(R.string.text_unknown)
    setToolbar()
  }

  private fun setToolbar() {
    binding.toolbar.title = ""
    binding.tvTitle.text = title
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()

    return true
  }

  override fun onBackPressed() {
    if (binding.wvArticle.canGoBack()) {
      binding.wvArticle.goBack()
    } else {
      super.onBackPressed()
    }
  }
}
