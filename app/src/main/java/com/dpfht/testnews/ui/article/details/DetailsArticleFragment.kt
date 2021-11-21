package com.dpfht.testnews.ui.article.details

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.dpfht.testnews.databinding.FragmentDetailsArticleBinding
import com.dpfht.testnews.ui.base.BaseFragment

class DetailsArticleFragment: BaseFragment() {

  private lateinit var binding: FragmentDetailsArticleBinding
  private var url = ""
  private var title = ""

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)
    binding = FragmentDetailsArticleBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val args = DetailsArticleFragmentArgs.fromBundle(requireArguments())

    url = args.url
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

    title = args.title

    setToolbar()
    setHandleBackPressed()
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = title
  }

  private fun setHandleBackPressed() {
    val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        if (binding.wvArticle.canGoBack()) {
          binding.wvArticle.goBack()
        } else {
          NavHostFragment.findNavController(this@DetailsArticleFragment).navigateUp()
        }
      }
    }

    requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
  }
}
