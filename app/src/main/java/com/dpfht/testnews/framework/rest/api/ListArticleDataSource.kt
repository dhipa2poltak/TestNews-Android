package com.dpfht.testnews.framework.rest.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dpfht.testnews.framework.rest.api.ListArticleRepositoryImpl.Companion.DEFAULT_PAGE_INDEX
import com.dpfht.testnews.framework.rest.api.ListArticleRepositoryImpl.Companion.DEFAULT_PAGE_SIZE
import com.dpfht.testnews.data.model.remote.Article

class ListArticleDataSource(private val restService: RestService, private val sourceId: String, private val query: String?): PagingSource<Int, Article>() {

  var state: MutableLiveData<State> = MutableLiveData()

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
    updateState(State.LOADING)

    val page = params.key ?: DEFAULT_PAGE_INDEX

    return try {
      val response = restService.getArticles(page, DEFAULT_PAGE_SIZE, sourceId, query)
      updateState(State.DONE)
      LoadResult.Page(
        response.articles, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
        nextKey = if (response.articles.isEmpty()) null else page + 1
      )
    } catch (e: Exception) {
      e.printStackTrace()
      updateState(State.ERROR)
      LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, Article>): Int {
    return 0
  }

  private fun updateState(state: State) {
    this.state.postValue(state)
  }
}
