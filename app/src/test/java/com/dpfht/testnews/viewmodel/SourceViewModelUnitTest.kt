package com.dpfht.testnews.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.testnews.MainCoroutineRule
import com.dpfht.testnews.data.model.remote.Source
import com.dpfht.testnews.domain.model.GetSourceResult
import com.dpfht.testnews.ui.source.SourceViewModel
import com.dpfht.testnews.usecase.GetSourceUseCase
import com.dpfht.testnews.usecase.UseCaseResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SourceViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: SourceViewModel

  @Mock
  private lateinit var getSourceUseCase: GetSourceUseCase

  @Mock
  private lateinit var sourceDataObserver: Observer<List<Source>>

  @Mock
  private lateinit var toastMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = SourceViewModel(getSourceUseCase)
  }

  @Test
  fun `fetch source successfully`() = runBlocking {
    val source1 = Source(
      id = "01",
      name = "Test Source 1",
      description = "desc source 1",
      url = "https://test/source1",
      category = "test",
      language = "english",
      country = "id"
    )

    val source2 = Source(
      id = "02",
      name = "Test Source 2",
      description = "desc source 2",
      url = "https://test/source2",
      category = "test",
      language = "english",
      country = "id"
    )

    val sources = listOf(source1, source2)
    val getSourceResult = GetSourceResult(sources)
    val result = UseCaseResultWrapper.Success(getSourceResult)

    whenever(getSourceUseCase.invoke(any())).thenReturn(result)

    viewModel.sourceData.observeForever(sourceDataObserver)
    viewModel.setCategoryName("test")
    viewModel.start()

    verify(sourceDataObserver).onChanged(eq(sources))
  }

  @Test
  fun `failed fetch source`() = runBlocking {
    val msg = "error fetch source"
    val result = UseCaseResultWrapper.ErrorResult(msg)

    whenever(getSourceUseCase.invoke(any())).thenReturn(result)

    viewModel.toastMessage.observeForever(toastMessageObserver)
    viewModel.setCategoryName("test")
    viewModel.start()

    verify(toastMessageObserver).onChanged(eq(msg))
  }
}
