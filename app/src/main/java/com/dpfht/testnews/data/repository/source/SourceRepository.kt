package com.dpfht.testnews.data.repository.source

import com.dpfht.testnews.data.model.remote.SourceResponse
import com.dpfht.testnews.data.api.rest.ResultWrapper

interface SourceRepository {

  suspend fun getSources(category: String): ResultWrapper<SourceResponse>
}