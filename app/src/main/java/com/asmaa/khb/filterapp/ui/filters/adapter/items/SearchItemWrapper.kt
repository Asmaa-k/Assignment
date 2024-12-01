package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.SearchBarViewHolder
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

//####################################################//
//##################### Extras #######################//
//####################################################//
class SearchViewHolderExtras(
    val categoryLabel: String = ""
) : ViewHolderExtras


//####################################################//
//##################### VIEW HOLDER CONTRACT #######################//
//####################################################//
val SEARCH_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = SearchBarViewHolder::class.java,
    layoutResId = R.layout.item_search_bar,
    callbackClass = CategoryAndSubCategoryActionCallback::class.java,
    extrasClass = SearchViewHolderExtras::class.java,
)


//####################################################//
//##################### VIEW ITEM #######################//
//####################################################//
class SearchItemWrapper : ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = SEARCH_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = "SEARCH_VIEW_HOLDER"
    override fun areContentsTheSame(newItem: ListItem): Boolean = this == newItem
}
