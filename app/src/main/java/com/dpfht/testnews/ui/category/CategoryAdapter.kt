package com.dpfht.testnews.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dpfht.testnews.databinding.RowCategoryBinding

class CategoryAdapter(private val categories: ArrayList<String>): RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

  private lateinit var binding: RowCategoryBinding

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
    binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return CategoryHolder(binding)
  }

  override fun getItemCount(): Int {
    return categories.size
  }

  override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
    holder.bindData(categories[position])
  }

  fun addData(datas: List<String>) {
    for (data in datas) {
      categories.add(data)
      notifyItemInserted(categories.size - 1)
    }
  }

  /*
  fun clearData() {
    categories.clear()
    notifyDataSetChanged()
  }
  */

  inner class CategoryHolder(private val binding: RowCategoryBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindData(categoryName: String) {
      binding.tvCategory.text = categoryName
      binding.root.setOnClickListener {
        onCategoryClick?.invoke(categoryName)
      }
    }
  }

  var onCategoryClick: ((String) -> (Unit))? = null
}
