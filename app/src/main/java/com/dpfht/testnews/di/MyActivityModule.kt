package com.dpfht.testnews.di

import androidx.appcompat.app.AlertDialog
import com.dpfht.testnews.R
import com.dpfht.testnews.features.article.list.ListArticleAdapter
import com.dpfht.testnews.features.article.list.ListArticleRepository
import com.dpfht.testnews.features.article.list.ListArticleRepositoryImpl
import com.dpfht.testnews.features.base.BaseActivity
import com.dpfht.testnews.features.category.CategoryAdapter
import com.dpfht.testnews.features.category.CategoryRepository
import com.dpfht.testnews.features.category.CategoryRepositoryImpl
import com.dpfht.testnews.features.category.CategoryViewModel
import com.dpfht.testnews.features.source.SourceAdapter
import com.dpfht.testnews.features.source.SourceRepository
import com.dpfht.testnews.features.source.SourceRepositoryImplement
import com.dpfht.testnews.features.source.SourceViewModel
import com.dpfht.testnews.rest.RestService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myActivityModule = module {

  factory { provideCategoryRepository(androidContext().resources.getStringArray(R.array.arr_news_categories).toList()) }
  factory { provideCategoryAdapter(get()) }
  factory { provideSourceRepository(get()) }
  factory { provideSourceAdapter(get()) }
  factory { provideListArticleRepository(get()) }
  factory { provideListArticleAdapter() }

  factory { provideLoadingDialog(it[0]) }

  /*
  factory { provideGenreAdapter(it[0]) }
  factory { provideActivityGenreBinding(it[0], it[1]) }

  factory { provideMovieByGenreAdapter(it[0]) }
  factory { provideActivityMovieByGenreBinding(it[0], it[1]) }

  factory { provideActivityMovieDetailBinding(it[0], it[1]) }

  factory { provideReviewAdapter(it[0]) }
  factory { provideActivityMovieReviewBinding(it[0], it[1]) }

  factory { provideLoadingDialog(it[0]) }
  */
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
  return SourceAdapter(sourceViewModel.sources)
}

fun provideListArticleRepository(restService: RestService): ListArticleRepository {
  return ListArticleRepositoryImpl(restService)
}

fun provideListArticleAdapter(): ListArticleAdapter {
  return ListArticleAdapter()
}

fun provideLoadingDialog(activity: BaseActivity): AlertDialog {
  return AlertDialog.Builder(activity)
    .setCancelable(false)
    .setView(R.layout.dialog_loading)
    .create()
}
