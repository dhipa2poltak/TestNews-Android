package com.dpfht.testnews.framework.rest.api

import com.dpfht.testnews.data.model.remote.SourceResponse
import com.dpfht.testnews.data.repository.source.SourceRepository
import kotlinx.coroutines.Dispatchers

class SourceRepositoryImpl(private val restService: RestService): SourceRepository {

  override suspend fun getSources(category: String): ResultWrapper<SourceResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getSources(category) }
  }
}
