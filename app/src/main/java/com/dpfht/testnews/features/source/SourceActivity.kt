package com.dpfht.testnews.features.source

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.Constant
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.ActivitySourceBinding
import com.dpfht.testnews.features.article.list.ListArticleActivity
import com.dpfht.testnews.features.base.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SourceActivity : BaseActivity() {

  private lateinit var binding: ActivitySourceBinding
  private val viewModel: SourceViewModel by viewModel()
  private val adapter: SourceAdapter by inject()

  private lateinit var categoryName: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySourceBinding.inflate(layoutInflater)
    setContentView(binding.root)

    categoryName = intent.getStringExtra(Constant.KEY_EXTRA_CATEGORY_NAME) ?: resources.getString(R.string.text_unknown)

    setToolbar()

    binding.rvSource.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.rvSource.adapter = adapter

    adapter.onClickSource = { source ->
      val itn = Intent(this@SourceActivity, ListArticleActivity::class.java)
      itn.putExtra(Constant.KEY_EXTRA_CATEGORY_NAME, categoryName)
      itn.putExtra(Constant.KEY_EXTRA_SOURCE_NAME, source.name)
      itn.putExtra(Constant.KEY_EXTRA_SOURCE_ID, source.id)
      startActivity(itn)
    }

    viewModel.sourceData.observe(this, {
      if (it.isNotEmpty()) {
        adapter.addData(it)
        viewModel.resetSourceData()
      }
    })

    viewModel.clearSourceData.observe(this, {
      if (it) {
        adapter.clearData()
        viewModel.resetClearSourceData()
      }
    })

    binding.etSearchSource.addTextChangedListener {
      viewModel.doFilter(it.toString())
    }

    viewModel.isShowDialogLoading.observe(this, { value ->
      if (value) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
      }
    })

    viewModel.toastMessage.observe(this, { value ->
      if (value != null && value.isNotEmpty()) {
        Toast.makeText(this@SourceActivity, value, Toast.LENGTH_SHORT).show()
      }
    })

    if (viewModel.sources.size == 0) {
      viewModel.start(categoryName)
    }
  }

  private fun setToolbar() {
    binding.toolbar.title = ""

    val str = "${resources.getString(R.string.text_source)}s of $categoryName"
    binding.tvTitle.text = str

    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()

    return true
  }
}
