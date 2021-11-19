package com.dpfht.testnews.features.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dpfht.testnews.features.base.BaseViewModel

class CategoryViewModel(private val repository: CategoryRepository): BaseViewModel() {

  var categories = ArrayList<String>()

  private val _categoryData = MutableLiveData<List<String>>()
  val categoryData: LiveData<List<String>> get() = _categoryData

  fun resetCategoryData() {
    _categoryData.value = arrayListOf()
  }

  fun start() {
    val datas = repository.getCategories()
    if (datas.isNotEmpty()) {
      _categoryData.postValue(datas)
    }
  }
}
