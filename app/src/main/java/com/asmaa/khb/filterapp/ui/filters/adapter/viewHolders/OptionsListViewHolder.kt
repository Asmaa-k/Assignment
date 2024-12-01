package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders


import androidx.core.view.isVisible
import com.asmaa.khb.filterapp.databinding.ItemParamOptionsFromDialogBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.DialogOptionsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.OptionListViewHolderExtras
import com.asmaa.khb.filterapp.ui.filters.adapter.items.OptionsItemWrapper
import com.bumptech.glide.Glide
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


class OptionsListViewHolder(
    private val binding: ItemParamOptionsFromDialogBinding,
    private val callback: DialogOptionsActionCallback,
    private val extras: OptionListViewHolderExtras,
) : BaseBindingViewHolder<OptionsItemWrapper>(binding, callback) {

    override fun onBind(listItem: OptionsItemWrapper) {
        //setup text
        binding.textLabel.text = listItem.option.value

        //setup image
        Glide.with(itemView).load(listItem.option.icon)
            .into(binding.imgIcon)

        //setup proper action
        if (!extras.showNavIcon) {
            setupCheckBox(listItem)
        } else {
            setupNavIcon()
        }
    }

    private fun setupCheckBox(listItem: OptionsItemWrapper) {
        binding.imgArrow.isVisible = false
        binding.checkbox.isVisible = true
        binding.checkbox.isChecked = listItem.option.isItemSelected == true
        binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isPressed) {
                callback.onOptionSelected(isChecked, listItem.option)
            }
        }
    }

    private fun setupNavIcon() {
        binding.imgArrow.isVisible = true
        binding.checkbox.isVisible = false
        binding.container.setOnClickListener {
            callback.onOptionSelected(true, listItem.option)
        }
    }
}