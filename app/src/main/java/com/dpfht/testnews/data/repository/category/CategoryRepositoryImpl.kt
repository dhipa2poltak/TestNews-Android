package com.dpfht.testnews.data.repository.category

class CategoryRepositoryImpl(private val categories: List<String>): CategoryRepository {

  override fun getCategories(): List<String> {
    return categories
  }
}
