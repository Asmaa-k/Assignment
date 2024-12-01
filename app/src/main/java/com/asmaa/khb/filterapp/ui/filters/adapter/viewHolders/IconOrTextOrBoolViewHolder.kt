package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders

import androidx.core.view.isVisible
import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.databinding.ItemParamIconOrTextOrBoolBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.BOOL_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.ICON_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.TEXT_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.InnerParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextOrBoolItemWrapper
import com.asmaa.khb.filterapp.ui.filters.adapter.items.TypeViewHolderExtras
import com.asmaa.khb.filterapp.ui.filters.util.Util.toDp
import com.bumptech.glide.Glide
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


class IconOrTextOrBoolViewHolder(
    private val binding: ItemParamIconOrTextOrBoolBinding,
    private val callback: InnerParamsActionCallback,
    private val extras: TypeViewHolderExtras,
) : BaseBindingViewHolder<IconOrTextOrBoolItemWrapper>(binding, callback) {

    override fun onBind(listItem: IconOrTextOrBoolItemWrapper) {
        //setup the proper view
        when (extras.viewType) {
            TEXT_TYPE -> {
                setupStringView(listItem, true)
            }

            BOOL_TYPE -> {
                setupStringView(listItem, false)
            }

            ICON_TYPE -> {
                setupIconView(listItem)
            }
        }

        //setup clicks
        binding.container.setOnClickListener {
            listItem.option.isItemSelected = !listItem.option.isItemSelected
            callback.onParamSelected(listItem.option)
        }
    }

    private fun setupStringView(listItem: IconOrTextOrBoolItemWrapper, isCircleShape: Boolean) {
        binding.imgOption.isVisible = false
        binding.txtOptionLabel.text = listItem.option.value
        binding.txtContainer.apply {
            isVisible = true
            isSelected = listItem.option.isItemSelected
            if (isCircleShape) {
                setBackgroundResource(R.drawable.circle_shape_item_selector)
                layoutParams.height = 65.toDp(context)
                layoutParams.width = 65.toDp(context)
                binding.txtOptionLabel.isSelected = false
            } else {
                setBackgroundResource(R.drawable.border_rectangle_shape_item_selector)
                binding.txtOptionLabel.isSelected = listItem.option.isItemSelected
            }
        }
    }

    private fun setupIconView(listItem: IconOrTextOrBoolItemWrapper) {
        binding.imgOption.isVisible = true
        binding.txtContainer.isVisible = false
        binding.imgOption.isSelected = listItem.option.isItemSelected
        Glide.with(itemView).load(listItem.option.icon)
            .placeholder(R.drawable.ferrar_logo)
            .error(R.drawable.ferrar_logo)
            .into(binding.imgOption)

    }
}