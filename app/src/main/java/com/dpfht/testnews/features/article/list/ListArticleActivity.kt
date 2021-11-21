package com.dpfht.testnews.features.article.list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.Constant
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.ActivityListArticleBinding
import com.dpfht.testnews.features.article.details.DetailsArticleActivity
import com.dpfht.testnews.features.base.BaseActivity
import com.dpfht.testnews.net.State
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.Timer
import java.util.TimerTask

class ListArticleActivity : BaseActivity() {

  private lateinit var binding: ActivityListArticleBinding
  private val viewModel: ListArticleViewModel by viewModel()

  //private val adapter: ListArticleAdapter by inject()
  private var adapter = ListArticleAdapter()

  private lateinit var categoryName: String
  private lateinit var sourceName: String
  private lateinit var sourceId: String

  private var textSearch: String? = null
  private val handler = Handler(Looper.getMainLooper())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityListArticleBinding.inflate(layoutInflater)
    setContentView(binding.root)

    categoryName = intent.getStringExtra(Constant.KEY_EXTRA_CATEGORY_NAME) ?: resources.getString(R.string.text_unknown)
    sourceName = intent.getStringExtra(Constant.KEY_EXTRA_SOURCE_NAME) ?: resources.getString(R.string.text_unknown)
    sourceId = intent.getStringExtra(Constant.KEY_EXTRA_SOURCE_ID) ?: resources.getString(R.string.text_unknown)

    setToolbar()

    binding.rvArticle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.rvArticle.adapter = adapter

    adapter.onClickArticleItem = { article ->
      val itn = Intent(this@ListArticleActivity, DetailsArticleActivity::class.java)
      itn.putExtra(Constant.KEY_EXTRA_URL, article.url)
      itn.putExtra(Constant.KEY_EXTRA_TITLE, article.title)
      startActivity(itn)
    }

    binding.etSearchArticle.addTextChangedListener {
      doSearch(it.toString())
    }

    fetchNewsLiveData(null)
  }

  private fun setToolbar() {
    binding.toolbar.title = ""

    val str = "${resources.getString(R.string.text_article)}s of $sourceName source in $categoryName category"
    binding.tvTitle.text = str

    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  private fun fetchNewsLiveData(query: String?) {
    viewModel.fetchArticles(sourceId, query).observe(this, {
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

  private fun doSearch(textSearch: String) {
    if (textSearch.isEmpty()) {
      if (this.textSearch != null) {
        this.textSearch = null

        taskSearch?.cancel()
        createTimerTask()
        timer.schedule(taskSearch, Constant.TYPE_SEARCH_TEXT_DELAY_TIME)
      }
    } else if (textSearch != this.textSearch) {
      this.textSearch = textSearch

      taskSearch?.cancel()
      createTimerTask()
      timer.schedule(taskSearch, Constant.TYPE_SEARCH_TEXT_DELAY_TIME)
    }
  }

  private var timer = Timer()
  private var taskSearch: TimerTask? = null

  private fun createTimerTask() {
    taskSearch = object : TimerTask() {
      override fun run() {
        handler.post {
          adapter = ListArticleAdapter()
          binding.rvArticle.adapter = adapter
          fetchNewsLiveData(textSearch)
        }
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()

    return true
  }
}
