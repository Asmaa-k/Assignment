package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.CategoryViewHolder
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

//####################################################//
//##################### VIEW HOLDER CONTRACT  #######################//
//####################################################//
val CATEGORY_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = CategoryViewHolder::class.java,
    layoutResId = R.layout.item_category_subcategory,
    callbackClass = CategoryAndSubCategoryActionCallback::class.java,
)

//####################################################//
//##################### VIEW ITEM #######################//
//####################################################//
data class CategoryWrapperItem(val category: CategoryRealm) : ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = CATEGORY_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = category.id.toString()
    override fun areContentsTheSame(newItem: ListItem): Boolean = this == newItem
}