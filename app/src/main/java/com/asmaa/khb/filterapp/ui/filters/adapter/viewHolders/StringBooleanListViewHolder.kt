package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders


import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.databinding.ItemParamStringBooleanListBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.BOOL_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.InnerParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextOrBoolItemWrapper
import com.asmaa.khb.filterapp.ui.filters.adapter.items.StringBooleanListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.TypeViewHolderExtras
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


class StringBooleanListViewHolder(
    private val binding: ItemParamStringBooleanListBinding,
    private val callback: ParamsActionCallback
) : BaseBindingViewHolder<StringBooleanListWrapperItem>(binding) {
    private val mAdapter: BlenderListAdapter by lazy {
        BlenderListAdapter(context, object : InnerParamsActionCallback {
            override fun onParamSelected(selectedOption: OptionsRealm) {
                callback.onSingleOptionSelected(listItem, selectedOption)
            }
        })
    }

    override fun onBind(listItem: StringBooleanListWrapperItem) {
        //setup text-view
        binding.txtLabel.text = listItem.fields.label

        //setup recyclerView and adapter
        setupRecyclerView(listItem.options)
    }

    private fun setupRecyclerView(options: List<OptionsRealm>) = binding.rvList.apply {
        layoutManager = mLayoutManager()
        mAdapter.setupAdapter(options)
        adapter = mAdapter
    }

    private fun mLayoutManager(): LayoutManager = FlexboxLayoutManager(context).apply {
        flexDirection = FlexDirection.ROW
        justifyContent = JustifyContent.FLEX_START
        flexWrap = FlexWrap.WRAP
    }

    private fun BlenderListAdapter.setupAdapter(options: List<OptionsRealm>) {
        val items = options.map { IconOrTextOrBoolItemWrapper(it) }
        setExtraParams(TypeViewHolderExtras(BOOL_TYPE))
        submitList(items)
    }
}