package com.dpfht.testnews.data.repository.category

import com.dpfht.testnews.usecase.UseCaseResultWrapper

class CategoryRepositoryImpl(
  private val categoryDataSource: CategoryDataSource
): CategoryRepository {

  override fun getCategories(): UseCaseResultWrapper<List<String>> {
    return categoryDataSource.getCategories()
  }
}
