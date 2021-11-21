package com.dpfht.testnews.ui.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.FragmentSourceBinding
import com.dpfht.testnews.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SourceFragment: BaseFragment() {

  private lateinit var binding: FragmentSourceBinding
  private val viewModel: SourceViewModel by viewModel()
  private val adapter: SourceAdapter by inject()

  private lateinit var categoryName: String

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)
    binding = FragmentSourceBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val args = SourceFragmentArgs.fromBundle(requireArguments())
    categoryName = args.categoryName

    setToolbar()

    binding.rvSource.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvSource.adapter = adapter

    adapter.onClickSource = { source ->
      val action = SourceFragmentDirections.actionSourceFragmentToListArticleFragment()
      action.categoryName = categoryName
      action.sourceName = source.name
      action.sourceId = source.id
      Navigation.findNavController(requireView()).navigate(action)
    }

    viewModel.sourceData.observe(requireActivity(), {
      if (it.isNotEmpty()) {
        adapter.addData(it)
        viewModel.resetSourceData()
      }
    })

    viewModel.clearSourceData.observe(requireActivity(), {
      if (it) {
        adapter.clearData()
        viewModel.resetClearSourceData()
      }
    })

    binding.etSearchSource.addTextChangedListener {
      viewModel.doFilter(it.toString())
    }

    viewModel.isShowDialogLoading.observe(requireActivity(), { value ->
      if (value) {
        prgDialog.show()
      } else {
        prgDialog.dismiss()
      }
    })

    viewModel.toastMessage.observe(requireActivity(), { value ->
      if (value != null && value.isNotEmpty()) {
        Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
      }
    })

    if (viewModel.sources.size == 0) {
      viewModel.start(categoryName)
    }
  }

  private fun setToolbar() {
    val str = "${resources.getString(R.string.text_source)}s of $categoryName"
    (requireActivity() as AppCompatActivity).supportActionBar?.title = str
  }

  override fun onResume() {
    super.onResume()

    val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        NavHostFragment.findNavController(this@SourceFragment).navigateUp()
      }
    }

    requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
  }
}
