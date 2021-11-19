package com.dpfht.testnews.di

import com.dpfht.testnews.features.category.CategoryViewModel
import com.dpfht.testnews.features.source.SourceViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    CategoryViewModel(get())
  }

  viewModel {
    SourceViewModel(get())
  }
}
