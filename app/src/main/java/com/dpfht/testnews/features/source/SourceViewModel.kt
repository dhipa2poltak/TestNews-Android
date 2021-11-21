package com.dpfht.testnews.features.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.testnews.features.base.BaseViewModel
import com.dpfht.testnews.model.Source
import com.dpfht.testnews.model.SourceResponse
import com.dpfht.testnews.net.ResultWrapper.GenericError
import com.dpfht.testnews.net.ResultWrapper.NetworkError
import com.dpfht.testnews.net.ResultWrapper.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SourceViewModel(private val sourceRepository: SourceRepository): BaseViewModel() {

  val sources = ArrayList<Source>()
  val sourcesFilter = ArrayList<Source>()

  private val _sourceData = MutableLiveData<List<Source>>()
  val sourceData: LiveData<List<Source>> get() = _sourceData

  private val _clearSourceData = MutableLiveData(false)
  val clearSourceData: LiveData<Boolean> get() = _clearSourceData

  fun resetSourceData() {
    _sourceData.value = arrayListOf()
  }

  fun resetClearSourceData() {
    _clearSourceData.value = false
  }

  fun start(category: String) {
    doGetSource(category)
  }

  private fun doGetSource(category: String) {
    isShowDialogLoading.postValue(true)
    viewModelScope.launch(Dispatchers.Main) {
      when (val sourceResponse = sourceRepository.getSources(category.lowercase())) {
        is NetworkError -> toastMessage.value = "network error"
        is GenericError -> toastMessage.value =
          "error ${sourceResponse.code} ${sourceResponse.error?.statusMessage}"
        is Success -> doSuccess(sourceResponse.value)
      }

      isShowDialogLoading.postValue(false)
      isLoadingData = false
    }
  }

  private fun doSuccess(sourceResponse: SourceResponse) {
    val datas = sourceResponse.sources
    if (datas.isNotEmpty()) {
      sources.clear()
      sources.addAll(datas)
      //_sourceData.postValue(datas)

      doFilter()
    }
  }

  fun doFilter(filterText: String = "") {
    //sourcesFilter.clear()
    _clearSourceData.value = true

    if (filterText.isEmpty()) {
      _sourceData.postValue(sources)
    } else {
      _sourceData.postValue(sources.filter { it.name.lowercase().contains(filterText.lowercase()) })
    }
  }
}
