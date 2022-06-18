package com.dpfht.testnews.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dpfht.testnews.data.model.remote.Article

interface GetArticleUseCase {

  operator fun invoke(sourceId: String, query: String?): LiveData<PagingData<Article>>
}
