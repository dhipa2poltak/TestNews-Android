package com.dpfht.testnews.ui.article.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.Constant
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.FragmentListArticleBinding
import com.dpfht.testnews.net.State
import com.dpfht.testnews.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.Timer
import java.util.TimerTask

class ListArticleFragment: BaseFragment() {

  private lateinit var binding: FragmentListArticleBinding
  private val viewModel: ListArticleViewModel by viewModel()

  //private val adapter: ListArticleAdapter by inject()
  private var adapter = ListArticleAdapter()

  private lateinit var categoryName: String
  private lateinit var sourceName: String
  private lateinit var sourceId: String

  private var textSearch: String? = null
  private val handler = Handler(Looper.getMainLooper())

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)
    binding = FragmentListArticleBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val args = ListArticleFragmentArgs.fromBundle(requireArguments())
    categoryName = args.categoryName
    sourceName = args.sourceName
    sourceId = args.sourceId

    setToolbar()

    binding.rvArticle.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvArticle.adapter = adapter

    setClickListenerAdapter()

    binding.etSearchArticle.addTextChangedListener {
      doSearch(it.toString())
    }

    fetchNewsLiveData(null)
  }

  private fun setToolbar() {
    val str = "${resources.getString(R.string.text_article)}s of $sourceName source in $categoryName category"
    (requireActivity() as AppCompatActivity).supportActionBar?.title = str
  }

  private fun fetchNewsLiveData(query: String?) {
    viewModel.fetchArticles(sourceId, query).observe(requireActivity(), {
      lifecycleScope.launch {
        adapter.submitData(it)
      }
    })

    viewModel.getState()?.observe(requireActivity(), { state ->
      if (state == State.LOADING) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
      }
    })
  }

  private fun doSearch(textSearch: String) {
    if (textSearch.isEmpty()) {
      if (this.textSearch != null) {
        this.textSearch = null

        taskSearch?.cancel()
        createTimerTask()
        timer.schedule(taskSearch, Constant.TYPE_SEARCH_TEXT_DELAY_TIME)
      }
    } else if (textSearch != this.textSearch) {
      this.textSearch = textSearch

      taskSearch?.cancel()
      createTimerTask()
      timer.schedule(taskSearch, Constant.TYPE_SEARCH_TEXT_DELAY_TIME)
    }
  }

  private var timer = Timer()
  private var taskSearch: TimerTask? = null

  private fun createTimerTask() {
    taskSearch = object : TimerTask() {
      override fun run() {
        handler.post {
          adapter = ListArticleAdapter()
          setClickListenerAdapter()
          binding.rvArticle.adapter = adapter
          fetchNewsLiveData(textSearch)
        }
      }
    }
  }

  private fun setClickListenerAdapter() {
    adapter.onClickArticleItem = { article ->
      val action = ListArticleFragmentDirections.actionListArticleFragmentToDetailsArticleFragment()
      action.url = article.url
      action.title = article.title
      Navigation.findNavController(requireView()).navigate(action)
    }
  }

  override fun onResume() {
    super.onResume()

    val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        NavHostFragment.findNavController(this@ListArticleFragment).navigateUp()
      }
    }

    requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
  }
}
