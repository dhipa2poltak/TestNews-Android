package com.dpfht.testnews.features.source

import com.dpfht.testnews.model.SourceResponse
import com.dpfht.testnews.net.ResultWrapper

interface SourceRepository {

  suspend fun getSources(category: String): ResultWrapper<SourceResponse>
}