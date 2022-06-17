package com.dpfht.testnews.framework.di

import com.dpfht.testnews.ui.article.list.ListArticleViewModel
import com.dpfht.testnews.ui.category.CategoryViewModel
import com.dpfht.testnews.ui.source.SourceViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    CategoryViewModel(get())
  }

  viewModel {
    SourceViewModel(get())
  }

  viewModel {
    ListArticleViewModel(get())
  }
}
