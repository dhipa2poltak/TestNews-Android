package com.dpfht.testnews.di

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.testnews.R
import com.dpfht.testnews.data.api.rest.RestService
import com.dpfht.testnews.ui.article.list.adapter.ListArticleAdapter
import com.dpfht.testnews.data.repository.article.list.ListArticleRepository
import com.dpfht.testnews.data.repository.article.list.ListArticleRepositoryImpl
import com.dpfht.testnews.ui.category.adapter.CategoryAdapter
import com.dpfht.testnews.data.repository.category.CategoryRepository
import com.dpfht.testnews.data.repository.category.CategoryRepositoryImpl
import com.dpfht.testnews.ui.category.CategoryViewModel
import com.dpfht.testnews.ui.source.adapter.SourceAdapter
import com.dpfht.testnews.data.repository.source.SourceRepository
import com.dpfht.testnews.data.repository.source.SourceRepositoryImpl
import com.dpfht.testnews.ui.source.SourceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myActivityModule = module {

  factory { provideCategoryRepository(androidContext().resources.getStringArray(R.array.arr_news_categories).toList()) }
  factory { provideCategoryAdapter(it[0]) }
  factory { provideSourceRepository(get()) }
  factory { provideSourceAdapter(get()) }
  factory { provideListArticleRepository(get()) }
  factory { provideListArticleAdapter() }

  factory { provideLoadingDialog(it[0]) }
}

fun provideCategoryRepository(categories: List<String>): CategoryRepository {
  return CategoryRepositoryImpl(categories)
}

fun provideCategoryAdapter(categoryViewModel: CategoryViewModel): CategoryAdapter {
  return CategoryAdapter(categoryViewModel.categories)
}

fun provideSourceRepository(restService: RestService): SourceRepository {
  return SourceRepositoryImpl(restService)
}

fun provideSourceAdapter(sourceViewModel: SourceViewModel): SourceAdapter {
  //return SourceAdapter(sourceViewModel.sources)
  return SourceAdapter(sourceViewModel.sourcesFilter)
}

fun provideListArticleRepository(restService: RestService): ListArticleRepository {
  return ListArticleRepositoryImpl(restService)
}

fun provideListArticleAdapter(): ListArticleAdapter {
  return ListArticleAdapter()
}

fun provideLoadingDialog(activity: AppCompatActivity): AlertDialog {
  return AlertDialog.Builder(activity)
    .setCancelable(false)
    .setView(R.layout.dialog_loading)
    .create()
}
