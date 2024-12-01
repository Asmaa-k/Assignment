package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders


import com.asmaa.khb.filterapp.databinding.ItemCategorySubcategoryBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.SubCategoryWrapperItem
import com.bumptech.glide.Glide
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


//####################################################//
//##################### VIEW HOLDER #######################//
//####################################################//
class SubCategoryViewHolder(
    private val binding: ItemCategorySubcategoryBinding,
    private val callback: CategoryAndSubCategoryActionCallback,
) : BaseBindingViewHolder<SubCategoryWrapperItem>(binding, callback) {

    override fun onBind(listItem: SubCategoryWrapperItem) {
        listItem.subCategory.let { subCategory ->
            //set-text
            binding.textCategoryName.text = subCategory.label

            //load-icon
            Glide.with(itemView).load(subCategory.icon).into(binding.imgIcon)

            //setup-onclick
            binding.container.setOnClickListener { callback.onSubCategoryClick(subCategory.id) }
        }
    }
}