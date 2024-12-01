package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.InnerParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.IconOrTextOrBoolViewHolder
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

class TypeViewHolderExtras(
    val viewType: String = ""
) : ViewHolderExtras

val ICON_OR_TEXT_OR_BOOL_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = IconOrTextOrBoolViewHolder::class.java,
    layoutResId = R.layout.item_param_icon_or_text_or_bool,
    callbackClass = InnerParamsActionCallback::class.java,
    extrasClass = TypeViewHolderExtras::class.java,
)

data class IconOrTextOrBoolItemWrapper(val option: OptionsRealm) : ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = ICON_OR_TEXT_OR_BOOL_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = option.id
    override fun areContentsTheSame(newItem: ListItem): Boolean = option.equals(newItem)
}
