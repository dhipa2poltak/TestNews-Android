package com.dpfht.testnews.framework.rest.api

import com.dpfht.testnews.data.repository.category.CategoryRepository

class CategoryRepositoryImpl(private val categories: List<String>): CategoryRepository {

  override fun getCategories(): List<String> {
    return categories
  }
}
