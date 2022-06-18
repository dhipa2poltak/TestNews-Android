package com.dpfht.testnews.usecase

import androidx.lifecycle.LiveData
import com.dpfht.testnews.data.repository.article.list.ListArticleRepository
import com.dpfht.testnews.framework.rest.api.State

class GetStateUseCaseImpl(
  private val listArticleRepository: ListArticleRepository
): GetStateUseCase {

  override fun invoke(): LiveData<State>? {
    return listArticleRepository.getState()
  }
}