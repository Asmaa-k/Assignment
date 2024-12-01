package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.SubCategoryViewHolder
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

//####################################################//
//##################### VIEW HOLDER CONTRACT #######################//
//####################################################//
val SUBCATEGORY_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = SubCategoryViewHolder::class.java,
    layoutResId = R.layout.item_category_subcategory,
    callbackClass = CategoryAndSubCategoryActionCallback::class.java,
)

//####################################################//
//##################### VIEW ITEM #######################//
//####################################################//
data class SubCategoryWrapperItem(val subCategory: SubCategoryRealm) : ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = SUBCATEGORY_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = subCategory.id.toString()
    override fun areContentsTheSame(newItem: ListItem): Boolean = this == newItem
}
