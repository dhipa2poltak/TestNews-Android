package com.dpfht.testnews.features.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.ActivityCategoryBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CategoryActivity : AppCompatActivity() {

  private lateinit var binding: ActivityCategoryBinding
  private val viewModel: CategoryViewModel by viewModel()
  private val adapter: CategoryAdapter by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityCategoryBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setToolbar()

    binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    binding.rvCategory.adapter = adapter

    viewModel.categoryData.observe(this, {
      if (it.isNotEmpty()) {
        adapter.addData(it)
        viewModel.resetCategoryData()
      }
    })

    if (viewModel.categories.size == 0) {
      viewModel.start()
    }
  }

  private fun setToolbar() {
    binding.toolbar.title = ""
    binding.tvTitle.text = resources.getString(R.string.text_category)
    setSupportActionBar(binding.toolbar)
  }
}
