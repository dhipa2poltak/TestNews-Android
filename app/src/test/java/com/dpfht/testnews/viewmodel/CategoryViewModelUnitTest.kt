package com.dpfht.testnews.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dpfht.testnews.MainCoroutineRule
import com.dpfht.testnews.ui.category.CategoryViewModel
import com.dpfht.testnews.usecase.GetCategoryUseCase
import com.dpfht.testnews.usecase.UseCaseResultWrapper
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CategoryViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: CategoryViewModel

  @Mock
  private lateinit var getCategoryUseCase: GetCategoryUseCase

  @Mock
  private lateinit var categoryDataObserver: Observer<List<String>>

  @Mock
  private lateinit var toastMessageObserver: Observer<String>

  @Before
  fun setup() {
    viewModel = CategoryViewModel(getCategoryUseCase)
  }

  @Test
  fun `fetch category successfully`() = runBlocking {
    val categories = arrayListOf("Action", "Drama", "Horror")
    val result = UseCaseResultWrapper.Success(categories)
    whenever(getCategoryUseCase.invoke()).thenReturn(result)

    viewModel.categoryData.observeForever(categoryDataObserver)
    viewModel.start()

    verify(categoryDataObserver).onChanged(eq(categories))
    //Assert.assertEquals(categories, result.value)
  }

  @Test
  fun `failed fetch category`() = runBlocking {
    val msg = "error fetch category"
    val result = UseCaseResultWrapper.ErrorResult(msg)
    whenever(getCategoryUseCase.invoke()).thenReturn(result)

    viewModel.toastMessage.observeForever(toastMessageObserver)
    viewModel.start()

    verify(toastMessageObserver).onChanged(eq(msg))
  }
}
