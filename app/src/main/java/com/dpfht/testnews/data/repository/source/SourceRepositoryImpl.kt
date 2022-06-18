package com.dpfht.testnews.data.repository.source

import com.dpfht.testnews.domain.model.GetSourceResult
import com.dpfht.testnews.usecase.UseCaseResultWrapper

class SourceRepositoryImpl(
  private val remoteSourceDataSource: SourceDataSource
): SourceRepository {

  override suspend fun getSources(category: String): UseCaseResultWrapper<GetSourceResult> {
    return remoteSourceDataSource.getSources(category)
  }
}
