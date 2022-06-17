package com.dpfht.testnews.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpfht.testnews.R
import com.dpfht.testnews.databinding.FragmentCategoryBinding
import com.dpfht.testnews.ui.base.BaseFragment
import com.dpfht.testnews.ui.category.adapter.CategoryAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CategoryFragment: BaseFragment() {

  private lateinit var binding: FragmentCategoryBinding
  private val viewModel: CategoryViewModel by viewModel()
  private val adapter: CategoryAdapter by inject { parametersOf(viewModel) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    setHasOptionsMenu(true)
    binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setToolbar()

    binding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvCategory.adapter = adapter

    adapter.onCategoryClick = { categoryName ->
      val action = CategoryFragmentDirections.actionCategoryFragmentToSourceFragment(
        categoryName
      )
      Navigation.findNavController(requireView()).navigate(action)
    }

    viewModel.categoryData.observe(requireActivity()) {
      if (it.isNotEmpty()) {
        adapter.addData(it)
        viewModel.resetCategoryData()
      }
    }

    viewModel.start()
  }

  private fun setToolbar() {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.text_category)
  }

  override fun onResume() {
    super.onResume()

    val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
        requireActivity().finish()
      }
    }

    requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
  }
}
