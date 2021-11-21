package com.dpfht.testnews.ui.category

class CategoryRepositoryImpl(private val categories: List<String>): CategoryRepository {

  override fun getCategories(): List<String> {
    return categories
  }
}
