package com.dpfht.testnews.data.repository.source

import com.dpfht.testnews.data.model.remote.SourceResponse
import com.dpfht.testnews.data.api.rest.ResultWrapper
import com.dpfht.testnews.data.api.rest.safeApiCall
import com.dpfht.testnews.data.api.rest.RestService
import kotlinx.coroutines.Dispatchers

class SourceRepositoryImpl(private val restService: RestService): SourceRepository {

  override suspend fun getSources(category: String): ResultWrapper<SourceResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getSources(category) }
  }
}
