package com.dpfht.testnews.framework.di

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dpfht.testnews.R
import com.dpfht.testnews.R.array
import com.dpfht.testnews.framework.rest.api.RestService
import com.dpfht.testnews.ui.article.list.adapter.ListArticleAdapter
import com.dpfht.testnews.data.repository.article.list.ListArticleRepository
import com.dpfht.testnews.framework.rest.api.ListArticleRepositoryImpl
import com.dpfht.testnews.ui.category.adapter.CategoryAdapter
import com.dpfht.testnews.data.repository.category.CategoryRepository
import com.dpfht.testnews.data.repository.source.SourceDataSource
import com.dpfht.testnews.framework.rest.api.CategoryRepositoryImpl
import com.dpfht.testnews.ui.category.CategoryViewModel
import com.dpfht.testnews.ui.source.adapter.SourceAdapter
import com.dpfht.testnews.data.repository.source.SourceRepository
import com.dpfht.testnews.data.repository.source.SourceRepositoryImpl
import com.dpfht.testnews.framework.rest.api.RemoteSourceDataSourceImpl
import com.dpfht.testnews.ui.source.SourceViewModel
import com.dpfht.testnews.usecase.GetSourceUseCase
import com.dpfht.testnews.usecase.GetSourceUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myActivityModule = module {

  factory { provideCategoryRepository(androidContext().resources.getStringArray(array.arr_news_categories).toList()) }
  factory { provideCategoryAdapter(it[0]) }

  factory { provideSourceDataSource(get()) }
  factory { provideSourceRepository(get()) }
  factory { provideGetSourceUseCase(get()) }

  factory { provideSourceAdapter(get()) }
  factory { provideListArticleRepository(get()) }
  factory { provideListArticleAdapter() }

  factory { provideLoadingDialog(it[0]) }
}

fun provideSourceDataSource(restService: RestService): SourceDataSource {
  return RemoteSourceDataSourceImpl(restService)
}

fun provideSourceRepository(sourceDataSource: SourceDataSource): SourceRepository {
  return SourceRepositoryImpl(sourceDataSource)
}

fun provideGetSourceUseCase(sourceRepository: SourceRepository): GetSourceUseCase {
  return GetSourceUseCaseImpl(sourceRepository)
}

fun provideListArticleRepository(restService: RestService): ListArticleRepository {
  return ListArticleRepositoryImpl(restService)
}

fun provideCategoryRepository(categories: List<String>): CategoryRepository {
  return CategoryRepositoryImpl(categories)
}

fun provideCategoryAdapter(categoryViewModel: CategoryViewModel): CategoryAdapter {
  return CategoryAdapter(categoryViewModel.categories)
}

fun provideSourceAdapter(sourceViewModel: SourceViewModel): SourceAdapter {
  //return SourceAdapter(sourceViewModel.sources)
  return SourceAdapter(sourceViewModel.sourcesFilter)
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
