package com.dpfht.testnews.ui.source

import com.dpfht.testnews.model.SourceResponse
import com.dpfht.testnews.net.ResultWrapper
import com.dpfht.testnews.net.safeApiCall
import com.dpfht.testnews.rest.RestService
import kotlinx.coroutines.Dispatchers

class SourceRepositoryImplement(private val restService: RestService): SourceRepository {

  override suspend fun getSources(category: String): ResultWrapper<SourceResponse> {
    return safeApiCall(Dispatchers.IO) { restService.getSources(category) }
  }
}
