package com.dpfht.testnews.ui.article.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dpfht.testnews.ui.base.BaseViewModel
import com.dpfht.testnews.data.model.remote.Article
import com.dpfht.testnews.data.api.rest.State
import com.dpfht.testnews.data.repository.article.list.ListArticleRepository

class ListArticleViewModel(private val listArticleRepository: ListArticleRepository): BaseViewModel() {

  //var adapter = ListArticleAdapter()

  fun fetchArticles(sourceId: String, query: String?): LiveData<PagingData<Article>> {
    return listArticleRepository.getArticles(sourceId, query)
      .cachedIn(viewModelScope)
  }

  fun getState(): LiveData<State>? {
    return listArticleRepository.getState()
  }
}
