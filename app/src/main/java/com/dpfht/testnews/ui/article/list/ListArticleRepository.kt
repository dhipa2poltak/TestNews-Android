package com.dpfht.testnews.ui.article.list

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dpfht.testnews.model.Article
import com.dpfht.testnews.net.State

interface ListArticleRepository {

  fun getArticles(sourceId: String, query: String?): LiveData<PagingData<Article>>

  fun getState(): LiveData<State>?
}