package com.dpfht.testnews.di

import com.dpfht.testnews.R
import com.dpfht.testnews.features.category.CategoryAdapter
import com.dpfht.testnews.features.category.CategoryRepository
import com.dpfht.testnews.features.category.CategoryRepositoryImpl
import com.dpfht.testnews.features.category.CategoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val myActivityModule = module {

  factory { provideCategoryRepository(androidContext().resources.getStringArray(R.array.arr_news_categories).toList()) }
  factory { provideCategoryAdapter(get()) }

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
