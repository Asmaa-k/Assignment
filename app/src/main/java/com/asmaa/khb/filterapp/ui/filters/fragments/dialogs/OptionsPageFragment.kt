package com.asmaa.khb.filterapp.ui.filters.fragments.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.databinding.FragmentOptionsPageBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.DialogOptionsActionCallback
import com.asmaa.khb.filterapp.ui.filters.util.FIELD_LABEL_ARGS
import com.asmaa.khb.filterapp.ui.filters.util.LIST_OPTIONS_ARGS
import com.haizo.generaladapter.kotlin.setupVertical
import com.haizo.generaladapter.listadapter.BlenderListAdapter


class OptionsPageFragment : Fragment(), DialogOptionsActionCallback {

    companion object {
        fun newInstance(
            options: ArrayList<OptionsRealm>, fieldLabel: String,
            onOptionResult: (item: OptionsRealm) -> Unit
        ) = OptionsPageFragment().apply {
            val bundle = Bundle()
            bundle.putParcelableArrayList(LIST_OPTIONS_ARGS, options)
            bundle.putString(FIELD_LABEL_ARGS, fieldLabel)
            arguments = bundle
            mOnOptionResult = onOptionResult
        }
    }


    private lateinit var binding: FragmentOptionsPageBinding

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentOptionsPageBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        setupRecyclerview()
    }

    private fun setupHeader() {
        binding.txtLabel.text = fieldLabel
    }

    private fun setupRecyclerview() = binding.rvList.apply {
        setupVertical()
        adapter = mAdapter
        mAdapter.setupAdapter(options, true)
    }

    override fun onOptionSelected(isChecked: Boolean, optionsRealm: OptionsRealm) {
        optionsRealm.isItemSelected = isChecked
        mAdapter.updateOption(optionsRealm)
        mOnOptionResult.invoke(optionsRealm)
    }
}


