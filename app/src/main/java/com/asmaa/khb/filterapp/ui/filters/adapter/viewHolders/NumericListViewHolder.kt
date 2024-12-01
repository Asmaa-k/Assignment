package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders


import com.asmaa.khb.filterapp.databinding.ItemParamNumericListBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.NumericListWrapperItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

class NumericListViewHolder(
    private val binding: ItemParamNumericListBinding,
    private val callback: ParamsActionCallback
) : BaseBindingViewHolder<NumericListWrapperItem>(binding) {

    override fun onBind(listItem: NumericListWrapperItem) {
        //setup texts
        binding.txtLabel.text = listItem.fields.label
        binding.txtFromValue.text = listItem.options[0].value
        binding.txtToValue.text = listItem.options[listItem.options.size - 1].value

        //setup clicks
        binding.root.setOnClickListener {
            callback.openOptionsPagerDialogFragment(listItem, listItem.fields.label)
        }
    }
}