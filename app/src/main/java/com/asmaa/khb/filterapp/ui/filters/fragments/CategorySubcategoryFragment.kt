package com.asmaa.khb.filterapp.ui.filters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.databinding.FragmentCategorySubcategoryBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.viewmodel.FilterViewModel
import com.haizo.generaladapter.kotlin.setupVertical
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CategorySubcategoryFragment : BaseFragment(), CategoryAndSubCategoryActionCallback {

    @Inject
    lateinit var navController: NavController
    private lateinit var binding: FragmentCategorySubcategoryBinding
    private val viewModel: FilterViewModel by viewModels()
    private val mAdapter: BlenderListAdapter by lazy {
        BlenderListAdapter(requireContext(), this)
    }
    internal val args: CategorySubcategoryFragmentArgs by lazy {
        CategorySubcategoryFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentCategorySubcategoryBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupObservable()
        viewModel.getLocalList(args.categoryId)
    }

    private fun setupToolbar() = binding.toolbar.apply {
        val isCategoryScreen = args.categoryId == 0
        title =
            if (isCategoryScreen) getString(R.string.category_toolbar_title) else getString(R.string.subcategory_toolbar_title)
        setNavigationOnClickListener { navController.popBackStack() }
    }

    private fun setupRecyclerView() = binding.categoryRecyclerview.apply {
        setupVertical()
        adapter = mAdapter
        mAdapter.setExtraParams(getAdapterExtra())
    }

    private fun setupObservable() {
        observeCategoryList()
        observeSubCategoryList()
        observeLoadingList()
        observeError()
    }

    private fun observeCategoryList() = lifecycleScope.launch {
        viewModel.categoryList.collect { result ->
            mAdapter.submitList(getCategoryListItem(result))
        }
    }

    private fun observeSubCategoryList() = lifecycleScope.launch {
        viewModel.subcategoryList.collect { result ->
            mAdapter.submitList(getSubCategoryListItem(result))
        }
    }


    private fun observeLoadingList() = lifecycleScope.launch {
        viewModel.loading.collect { show ->
            if (show) showLoadingDialog() else hideLoadingDialog()
        }
    }

    private fun observeError() = lifecycleScope.launch {
        viewModel.error.collect { errorMessage ->
            showErrorDialog(errorMessage)
        }
    }

    override fun onCategoryClick(categoryId: Int, label: String) =
        navigateToSubCategoryScreen(categoryId, label)

    override fun onSubCategoryClick(subCategory: Int) =
        navigateToParamsScreen(subCategory)

    override fun onSearch(query: String) =
        viewModel.onSearchQuery(args.categoryId, query)
}