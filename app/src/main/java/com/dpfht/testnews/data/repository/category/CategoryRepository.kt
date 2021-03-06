package com.dpfht.testnews.data.repository.category

import com.dpfht.testnews.usecase.UseCaseResultWrapper

interface CategoryRepository {

  fun getCategories(): UseCaseResultWrapper<List<String>>

}
