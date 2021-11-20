package com.dpfht.testnews.features.article.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dpfht.testnews.features.article.list.ListArticleRepositoryImpl.Companion.DEFAULT_PAGE_INDEX
import com.dpfht.testnews.features.article.list.ListArticleRepositoryImpl.Companion.DEFAULT_PAGE_SIZE
import com.dpfht.testnews.model.Article
import com.dpfht.testnews.net.State
import com.dpfht.testnews.rest.RestService

class ListArticleDataSource(private val restService: RestService, private val sourceId: String): PagingSource<Int, Article>() {

  var state: MutableLiveData<State> = MutableLiveData()

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    updateState(State.LOADING)

    val page = params.key ?: DEFAULT_PAGE_INDEX

    try {
      val response = restService.getArticles(page, DEFAULT_PAGE_SIZE, sourceId)
      updateState(State.DONE)
      return LoadResult.Page(
        response.articles, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
        nextKey = if (response.articles.isEmpty()) null else page + 1
      )
    } catch (e: Exception) {
      e.printStackTrace()
      updateState(State.ERROR)
      return LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Article>): Int {
    return 0
  }

  private fun updateState(state: State) {
    this.state.postValue(state)
  }
}
