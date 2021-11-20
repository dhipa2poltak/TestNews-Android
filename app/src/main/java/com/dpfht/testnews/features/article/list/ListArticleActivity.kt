package com.dpfht.testnews.features.article.list

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.ActivityListArticleBinding
import com.dpfht.testnews.features.base.BaseActivity
import com.dpfht.testnews.net.State
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListArticleActivity : BaseActivity() {

  private lateinit var binding: ActivityListArticleBinding
  private val viewModel: ListArticleViewModel by viewModel()
  private val adapter: ListArticleAdapter by inject()

  private lateinit var categoryName: String
  private lateinit var sourceName: String
  private lateinit var sourceId: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityListArticleBinding.inflate(layoutInflater)
    setContentView(binding.root)

    categoryName = intent.getStringExtra("category_name") ?: "Unknown"
    sourceName = intent.getStringExtra("source_name") ?: "Unknown"
    sourceId = intent.getStringExtra("source_id") ?: "Unknown"

    setToolbar()

    binding.rvArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.rvArticle.adapter = adapter

    fetchNewsLiveData()
  }

  private fun setToolbar() {
    binding.toolbar.title = ""
    binding.tvTitle.text = "${resources.getString(R.string.text_article)}s of $sourceName source in $categoryName category"
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun fetchNewsLiveData() {
    viewModel.fetchArticles(sourceId).observe(this, {
      lifecycleScope.launch {
        adapter.submitData(it)
      }
    })

    viewModel.getState()?.observe(this, { state ->
      if (state == State.LOADING) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
      }
    })
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()

    return true
  }
}
