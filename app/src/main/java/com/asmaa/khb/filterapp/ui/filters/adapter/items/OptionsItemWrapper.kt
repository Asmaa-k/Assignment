package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.DialogOptionsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.OptionsListViewHolder
import com.haizo.generaladapter.interfaces.ViewHolderExtras
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

class OptionListViewHolderExtras(
    val showNavIcon: Boolean = false
) : ViewHolderExtras

val OPTIONS_LIST_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = OptionsListViewHolder::class.java,
    layoutResId = R.layout.item_param_options_from_dialog,
    callbackClass = DialogOptionsActionCallback::class.java,
    extrasClass = OptionListViewHolderExtras::class.java,
)


data class OptionsItemWrapper(val option: OptionsRealm) : ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = OPTIONS_LIST_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = option.id
    override fun areContentsTheSame(newItem: ListItem): Boolean = option.equals(newItem)
}