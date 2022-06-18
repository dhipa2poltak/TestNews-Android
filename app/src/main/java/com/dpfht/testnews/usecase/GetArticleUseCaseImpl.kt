package com.dpfht.testnews.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dpfht.testnews.data.model.remote.Article
import com.dpfht.testnews.data.repository.article.list.ListArticleRepository

class GetArticleUseCaseImpl(
  private val listArticleRepository: ListArticleRepository
): GetArticleUseCase {

  override operator fun invoke(sourceId: String, query: String?): LiveData<PagingData<Article>> {
    return listArticleRepository.getArticles(sourceId, query)
  }
}