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

  var sources = ArrayList<Source>()

  private val _sourceData = MutableLiveData<List<Source>>()
  val sourceData: LiveData<List<Source>> get() = _sourceData

  fun resetSourceData() {
    _sourceData.value = arrayListOf()
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
      _sourceData.postValue(datas)
    }
  }
}
