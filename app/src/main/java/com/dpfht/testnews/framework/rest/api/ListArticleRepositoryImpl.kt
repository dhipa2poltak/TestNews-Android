package com.dpfht.testnews.framework.rest.api

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dpfht.testnews.data.model.remote.Article
import com.dpfht.testnews.data.repository.article.list.ListArticleRepository

class ListArticleRepositoryImpl(private val restService: RestService): ListArticleRepository {

  companion object {
    const val DEFAULT_PAGE_INDEX = 1
    const val DEFAULT_PAGE_SIZE = 5
  }

  private var ds: ListArticleDataSource? = null

  override fun getArticles(sourceId: String, query: String?): LiveData<PagingData<Article>> {
    val pagingConfig = getDefaultPageConfig()
    ds = ListArticleDataSource(restService, sourceId, query)

    return Pager(
      config = pagingConfig,
      pagingSourceFactory = { ds!! }
    ).liveData
  }

  override fun getState(): LiveData<State>? {
    return ds?.state
  }

  private fun getDefaultPageConfig(): PagingConfig {
    return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
  }
}
