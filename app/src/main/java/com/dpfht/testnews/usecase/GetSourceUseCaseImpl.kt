package com.dpfht.testnews.usecase

import com.dpfht.testnews.data.repository.source.SourceRepository
import com.dpfht.testnews.domain.model.GetSourceResult

class GetSourceUseCaseImpl(
  private val sourceRepository: SourceRepository
): GetSourceUseCase {

  override suspend fun invoke(categoryName: String): UseCaseResultWrapper<GetSourceResult> {
    return sourceRepository.getSources(categoryName)
  }
}