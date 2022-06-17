package com.dpfht.testnews.framework.rest.api

import com.dpfht.testnews.data.model.remote.toDomain
import com.dpfht.testnews.data.repository.source.SourceRepository
import com.dpfht.testnews.domain.model.GetSourceResult
import kotlinx.coroutines.Dispatchers

class SourceRepositoryImpl(private val restService: RestService): SourceRepository {

  override suspend fun getSources(category: String): ResultWrapper<GetSourceResult> {
    return safeApiCall(Dispatchers.IO) { restService.getSources(category).toDomain() }
  }
}
