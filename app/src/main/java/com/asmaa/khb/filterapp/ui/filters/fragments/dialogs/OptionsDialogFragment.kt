package com.asmaa.khb.filterapp.ui.filters.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.databinding.DialogFragmentOptionsBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.DialogOptionsActionCallback
import com.asmaa.khb.filterapp.ui.filters.util.FIELD_LABEL_ARGS
import com.asmaa.khb.filterapp.ui.filters.util.LIST_OPTIONS_ARGS
import com.haizo.generaladapter.kotlin.setupVertical
import com.haizo.generaladapter.listadapter.BlenderListAdapter

class OptionsDialogFragment : DialogFragment(), DialogOptionsActionCallback {

    companion object {
        const val TAG = "OptionsDialogFragment"
        fun newInstance(
            options: ArrayList<OptionsRealm>,
            fieldLabel: String,
            onOptionsResult: (list: ArrayList<OptionsRealm>) -> Unit
        ) = OptionsDialogFragment().apply {
            val bundle = Bundle()
            bundle.putParcelableArrayList(LIST_OPTIONS_ARGS, options)
            bundle.putString(FIELD_LABEL_ARGS, fieldLabel)
            arguments = bundle
            mOnOptionsResult = onOptionsResult
        }
    }

    private lateinit var binding: DialogFragmentOptionsBinding
    private val mAdapter: BlenderListAdapter by lazy {
        BlenderListAdapter(requireContext(), this)
    }

    private val options: ArrayList<OptionsRealm> by lazy {
        requireArguments().getParcelableArrayList(LIST_OPTIONS_ARGS) ?: arrayListOf()
    }

    private val fieldLabel: String by lazy {
        requireArguments().getString(FIELD_LABEL_ARGS) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = DialogFragmentOptionsBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onStart() {
        super.onStart()
        dialog.setDialogLayoutParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearch()
        setupHeader()
        setupRecyclerview()
        setupClicks()
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterOptions(newText, options, mAdapter)
                return true
            }
        })
    }

    private fun setupHeader() {
        binding.txtLabel.text = fieldLabel
    }

    private fun setupRecyclerview() = binding.rvList.apply {
        setupVertical()
        adapter = mAdapter

        //submit list options
        mAdapter.setupAdapter(options, false)
    }

    private fun setupClicks() {
        binding.actionDone.setOnClickListener {
            mOnOptionsResult.invoke(selectedOption)
            dismiss()
        }

        binding.actionRest.setOnClickListener { restAllItems(mAdapter) }
        binding.actionCancel.setOnClickListener { dismiss() }
    }

    override fun onOptionSelected(isChecked: Boolean, optionsRealm: OptionsRealm) {
        if (isChecked) {
            selectedOption.add(optionsRealm)
        } else {
            selectedOption.remove(optionsRealm)
        }
        optionsRealm.isItemSelected = isChecked
        mAdapter.updateOption(optionsRealm)
    }
}


