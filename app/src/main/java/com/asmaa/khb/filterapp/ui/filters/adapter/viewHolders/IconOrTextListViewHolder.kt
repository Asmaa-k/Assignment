package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders


import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.databinding.ItemParamIconListBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.InnerParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextOrBoolItemWrapper
import com.asmaa.khb.filterapp.ui.filters.adapter.items.TypeViewHolderExtras
import com.haizo.generaladapter.kotlin.setupHorizontal
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


class IconOrTextListViewHolder(
    private val binding: ItemParamIconListBinding,
    private val callback: ParamsActionCallback,
) : BaseBindingViewHolder<IconOrTextListWrapperItem>(binding) {
    private val mAdapter: BlenderListAdapter by lazy {
        BlenderListAdapter(context, object : InnerParamsActionCallback {

            override fun onParamSelected(selectedOption: OptionsRealm) {
                callback.onSingleOptionSelected(listItem, selectedOption)
            }
        })
    }

    override fun onBind(listItem: IconOrTextListWrapperItem) {
        //setup texts
        binding.txtLabel.text = listItem.fields.label
        binding.txtOptions.text = listItem.fields.label

        //setup RecyclerView
        setupRecyclerView(listItem.options, listItem.type)

        //setup clicks
        listOf(binding.txtOptions, binding.icon).forEach {
            it.setOnClickListener {
                callback.openOptionsDialogFragment(listItem, listItem.fields.label)
            }
        }
    }

    private fun setupRecyclerView(options: List<OptionsRealm>, type: String) =
        binding.rvList.apply {
            setupHorizontal()
            val items = options.map { IconOrTextOrBoolItemWrapper(it) }
            mAdapter.setExtraParams(TypeViewHolderExtras(type))
            adapter = mAdapter
            mAdapter.submitList(items)
        }
}