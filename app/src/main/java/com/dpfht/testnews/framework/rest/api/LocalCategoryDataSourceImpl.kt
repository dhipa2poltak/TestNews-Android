package com.dpfht.testnews.framework.rest.api

import com.dpfht.testnews.data.repository.category.CategoryDataSource
import com.dpfht.testnews.usecase.UseCaseResultWrapper

class LocalCategoryDataSourceImpl(private val categories: List<String>): CategoryDataSource {

  override fun getCategories(): UseCaseResultWrapper<List<String>> {
    return UseCaseResultWrapper.Success(categories)
  }
}
