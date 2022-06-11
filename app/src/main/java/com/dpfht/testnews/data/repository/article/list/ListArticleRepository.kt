package com.dpfht.testnews.data.repository.article.list

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dpfht.testnews.data.model.remote.Article
import com.dpfht.testnews.data.api.rest.State

interface ListArticleRepository {

  fun getArticles(sourceId: String, query: String?): LiveData<PagingData<Article>>

  fun getState(): LiveData<State>?
}