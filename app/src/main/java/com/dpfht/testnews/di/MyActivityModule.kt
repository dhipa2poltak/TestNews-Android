package com.dpfht.testnews.di

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.testnews.R
import com.dpfht.testnews.rest.RestService
import com.dpfht.testnews.ui.article.list.ListArticleAdapter
import com.dpfht.testnews.ui.article.list.ListArticleRepository
import com.dpfht.testnews.ui.article.list.ListArticleRepositoryImpl
import com.dpfht.testnews.ui.category.CategoryAdapter
import com.dpfht.testnews.ui.category.CategoryRepository
import com.dpfht.testnews.ui.category.CategoryRepositoryImpl
import com.dpfht.testnews.ui.category.CategoryViewModel
import com.dpfht.testnews.ui.source.SourceAdapter
import com.dpfht.testnews.ui.source.SourceRepository
import com.dpfht.testnews.ui.source.SourceRepositoryImplement
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
  return SourceRepositoryImplement(restService)
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
