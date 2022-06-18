package com.dpfht.testnews.usecase

import com.dpfht.testnews.domain.model.GetSourceResult

interface GetSourceUseCase {

  suspend operator fun invoke(categoryName: String): UseCaseResultWrapper<GetSourceResult>
}
