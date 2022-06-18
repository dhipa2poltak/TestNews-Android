package com.dpfht.testnews.usecase

import com.dpfht.testnews.data.repository.category.CategoryRepository

class GetCategoryUseCaseImpl(
  private val categoryRepository: CategoryRepository
): GetCategoryUseCase {

  override suspend operator fun invoke(): UseCaseResultWrapper<List<String>> {
    return categoryRepository.getCategories()
  }
}