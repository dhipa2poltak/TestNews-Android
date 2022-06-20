package com.dpfht.testnews.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dpfht.testnews.MainCoroutineRule
import com.dpfht.testnews.ui.article.list.ListArticleViewModel
import com.dpfht.testnews.usecase.GetArticleUseCase
import com.dpfht.testnews.usecase.GetStateUseCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListArticleViewModelUnitTest {

  @get:Rule
  val taskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val coroutineRule = MainCoroutineRule()

  private lateinit var viewModel: ListArticleViewModel

  @Mock
  private lateinit var getArticleUseCase: GetArticleUseCase

  @Mock
  private lateinit var getStateUse: GetStateUseCase

  @Before
  fun setup() {
    viewModel = ListArticleViewModel(getArticleUseCase, getStateUse)
  }

  @Test
  fun `fetch article successfully`() {

  }
}
