package com.dpfht.testnews.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.testnews.ui.base.BaseViewModel
import com.dpfht.testnews.usecase.GetCategoryUseCase
import com.dpfht.testnews.usecase.UseCaseResultWrapper.ErrorResult
import com.dpfht.testnews.usecase.UseCaseResultWrapper.Success
import kotlinx.coroutines.launch

class CategoryViewModel(private val getCategoryUseCase: GetCategoryUseCase): BaseViewModel() {

  var categories = ArrayList<String>()
    private set

  private val _categoryData = MutableLiveData<List<String>>()
  val categoryData: LiveData<List<String>> get() = _categoryData

  fun resetCategoryData() {
    _categoryData.value = arrayListOf()
  }

  fun start() {
    if (categories.isEmpty()) {
      viewModelScope.launch {
        when (val result = getCategoryUseCase()) {
          is Success -> {
            if (result.value.isNotEmpty()) {
              categories = ArrayList(result.value)
              _categoryData.postValue(categories)
            }
          }
          is ErrorResult -> {

          }
        }
      }
    }
  }
}
