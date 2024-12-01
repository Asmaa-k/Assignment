package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders


import com.asmaa.khb.filterapp.databinding.ItemCategorySubcategoryBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.CategoryWrapperItem
import com.bumptech.glide.Glide
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


//####################################################//
//##################### VIEW HOLDER  #######################//
//####################################################//
class CategoryViewHolder(
    private val binding: ItemCategorySubcategoryBinding,
    private val callback: CategoryAndSubCategoryActionCallback
) : BaseBindingViewHolder<CategoryWrapperItem>(binding) {

    override fun onBind(listItem: CategoryWrapperItem) {
        listItem.category.let { category ->
            //set-text
            binding.textCategoryName.text = category.label

            //load-icon
            Glide.with(itemView).load(category.icon).into(binding.imgIcon)

            //setup-onclick
            binding.container.setOnClickListener {
                callback.onCategoryClick(category.id, category.label)
            }
        }
    }
}