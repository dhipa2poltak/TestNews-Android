package com.dpfht.testnews.usecase

interface GetCategoryUseCase {

  suspend operator fun invoke(): UseCaseResultWrapper<List<String>>
}
