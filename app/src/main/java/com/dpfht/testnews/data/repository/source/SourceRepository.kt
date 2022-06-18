package com.dpfht.testnews.data.repository.source

import com.dpfht.testnews.domain.model.GetSourceResult
import com.dpfht.testnews.usecase.UseCaseResultWrapper

interface SourceRepository {

  suspend fun getSources(category: String): UseCaseResultWrapper<GetSourceResult>
}