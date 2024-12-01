package com.asmaa.khb.filterapp.ui.filters.fragments.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.databinding.DialogFragmentOptionsPagerDialogBinding
import com.asmaa.khb.filterapp.ui.filters.util.FIELD_LABEL_ARGS
import com.asmaa.khb.filterapp.ui.filters.util.LIST_OPTIONS_ARGS
import com.google.android.material.tabs.TabLayoutMediator


class OptionsPagerDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "OptionsPagerDialogFragment"

        fun newInstance(
            options: ArrayList<OptionsRealm>,
            fieldLabel: String,
            onOptionsResult: (options: ArrayList<OptionsRealm>) -> Unit
        ) = OptionsPagerDialogFragment().apply {
            val bundle = Bundle()
            bundle.putParcelableArrayList(LIST_OPTIONS_ARGS, options)
            bundle.putString(FIELD_LABEL_ARGS, fieldLabel)
            arguments = bundle
            mOnOptionsResult = onOptionsResult
        }
    }

    internal lateinit var binding: DialogFragmentOptionsPagerDialogBinding
    internal lateinit var pagerAdapter: OptionsPagerAdapter
    private val options: ArrayList<OptionsRealm> by lazy {
        requireArguments().getParcelableArrayList(LIST_OPTIONS_ARGS) ?: arrayListOf()
    }
    private val fieldLabel: String by lazy {
        requireArguments().getString(FIELD_LABEL_ARGS) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DialogFragmentOptionsPagerDialogBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root

    override fun onStart() {
        super.onStart()
        dialog.setDialogLayoutParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPagerAdapter()
        setupTabLayout()
        setupClicks()
    }

    private fun setupPagerAdapter() {
        pagerAdapter = OptionsPagerAdapter(
            fragment = this, options = options, fieldLabel = fieldLabel
        ) { option ->
            setupPagerAction(option)
        }
        binding.viewPager.adapter = pagerAdapter
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Set tab titles based on the position
            when (position) {
                0 -> tab.text = getString(R.string.text_label_from)
                1 -> tab.text = getString(R.string.text_label_to)
            }
        }.attach()
    }

    private fun setupClicks() {
        binding.cancelAction.setOnClickListener { dismiss() }
    }
}
