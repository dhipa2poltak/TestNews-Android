package com.dpfht.testnews.di

import com.dpfht.testnews.features.category.CategoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    CategoryViewModel(get())
  }
}
