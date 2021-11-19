package com.dpfht.testnews.features.source

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.ActivitySourceBinding
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

    categoryName = intent.getStringExtra("category_name") ?: "Unknown"

    setToolbar()

    binding.rvSource.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.rvSource.adapter = adapter

    viewModel.sourceData.observe(this, {
      if (it.isNotEmpty()) {
        adapter.addData(it)
        viewModel.resetSourceData()
      }
    })

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
    binding.tvTitle.text = "${resources.getString(R.string.text_source)} of $categoryName"
    setSupportActionBar(binding.toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  override fun onSupportNavigateUp(): Boolean {
    finish()

    return true
  }
}
