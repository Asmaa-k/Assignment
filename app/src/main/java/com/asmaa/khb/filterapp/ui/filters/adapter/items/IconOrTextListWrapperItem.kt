package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.IconOrTextListViewHolder
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract


val ICON_OR_TEXT_LIST_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = IconOrTextListViewHolder::class.java,
    layoutResId = R.layout.item_param_icon_list,
    callbackClass = ParamsActionCallback::class.java,
)

data class IconOrTextListWrapperItem(
    val fields: FieldRealm,
    val options: List<OptionsRealm>,
    val type: String
) : ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = ICON_OR_TEXT_LIST_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = fields.id
    override fun areContentsTheSame(newItem: ListItem): Boolean = false
}