package com.dpfht.testnews.ui.article.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dpfht.testnews.data.model.remote.Article
import com.dpfht.testnews.framework.rest.api.State
import com.dpfht.testnews.ui.base.BaseViewModel
import com.dpfht.testnews.usecase.GetArticleUseCase
import com.dpfht.testnews.usecase.GetStateUseCase

class ListArticleViewModel(
  private val getArticleUseCase: GetArticleUseCase,
  private val getStateUseCase: GetStateUseCase
): BaseViewModel() {

  //var adapter = ListArticleAdapter()

  fun fetchArticles(sourceId: String, query: String?): LiveData<PagingData<Article>> {
    return getArticleUseCase(sourceId, query).cachedIn(viewModelScope)
  }

  fun getState(): LiveData<State>? {
    return getStateUseCase()
  }
}
