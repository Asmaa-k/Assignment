package com.asmaa.khb.filterapp.ui.filters.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.databinding.FragmentParamsFilterBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.NumericListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.StringBooleanListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.fragments.dialogs.OptionsDialogFragment
import com.asmaa.khb.filterapp.ui.filters.fragments.dialogs.OptionsPagerDialogFragment
import com.asmaa.khb.filterapp.ui.filters.viewmodel.FilterViewModel
import com.haizo.generaladapter.kotlin.setupVertical
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ParamsFilterFragment : BaseFragment(), ParamsActionCallback {
    @Inject
    lateinit var navController: NavController
    private lateinit var binding: FragmentParamsFilterBinding
    private val viewModel: FilterViewModel by viewModels()
    internal val mAdapter: BlenderListAdapter by lazy {
        BlenderListAdapter(requireContext(), this)
    }

    private val args: ParamsFilterFragmentArgs by lazy {
        ParamsFilterFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = FragmentParamsFilterBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecyclerView()
        setupObservable()
        viewModel.getDynamicParamsLocal(args.subcategoryId)
    }

    private fun setupToolbar() = binding.toolbar.apply {
        title = getString(R.string.params_toolbar_title)
        setNavigationOnClickListener { navController.popBackStack() }
    }

    private fun setupRecyclerView() = binding.paramsRecyclerview.apply {
        setupVertical()
        adapter = mAdapter
        setItemViewCacheSize(20)
    }

    private fun setupObservable() {
        observeFieldsOptionsPramsList()
        observeLoadingList()
        observeError()
    }

    private fun observeFieldsOptionsPramsList() = lifecycleScope.launch {
        viewModel.fieldsOptionsPrams.collect { result ->
            mAdapter.submitList(getParamsListItemWrappers(result))
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

    override fun openOptionsDialogFragment(field: IconOrTextListWrapperItem, fieldLabel: String) =
        OptionsDialogFragment.newInstance(
            field.options.toCollection(ArrayList()), fieldLabel
        ) { selectedOptions ->
            openOptionsDialogFragmentResult(field, selectedOptions)
        }.show(parentFragmentManager, OptionsDialogFragment.TAG)

    override fun openOptionsPagerDialogFragment(field: NumericListWrapperItem, fieldLabel: String) =
        OptionsPagerDialogFragment.newInstance(
            field.options.toCollection(ArrayList()), fieldLabel
        ) { selectedOptions ->
            openOptionsPagerDialogFragmentResult(field, selectedOptions)
        }.show(parentFragmentManager, OptionsPagerDialogFragment.TAG)


    override fun onSingleOptionSelected(
        field: IconOrTextListWrapperItem, selectedOption: OptionsRealm
    ) = onSingleOptionSelectedResult(field, selectedOption)

    override fun onSingleOptionSelected(
        field: StringBooleanListWrapperItem, selectedOption: OptionsRealm
    ) = onSingleOptionSelectedResult(field, selectedOption)
}