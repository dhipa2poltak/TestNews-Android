package com.dpfht.testnews.framework.rest.api

import com.dpfht.testnews.data.model.remote.toDomain
import com.dpfht.testnews.data.repository.source.SourceDataSource
import com.dpfht.testnews.domain.model.GetSourceResult
import com.dpfht.testnews.framework.rest.api.ResultWrapper.GenericError
import com.dpfht.testnews.framework.rest.api.ResultWrapper.NetworkError
import com.dpfht.testnews.framework.rest.api.ResultWrapper.Success
import com.dpfht.testnews.usecase.UseCaseResultWrapper
import com.dpfht.testnews.usecase.UseCaseResultWrapper.ErrorResult
import kotlinx.coroutines.Dispatchers

class RemoteSourceDataSourceImpl(
  private val restService: RestService
): SourceDataSource {

  override suspend fun getSources(category: String): UseCaseResultWrapper<GetSourceResult> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getSources(category) }) {
      is Success -> {
        UseCaseResultWrapper.Success(result.value.toDomain())
      }
      is GenericError -> {
        if (result.code != null && result.error?.message != null) {
          ErrorResult("error ${result.code} ${result.error.message}")
        } else {
          ErrorResult("error in conversion")
        }
      }
      is NetworkError -> {
        ErrorResult("error in connection")
      }
    }
  }
}