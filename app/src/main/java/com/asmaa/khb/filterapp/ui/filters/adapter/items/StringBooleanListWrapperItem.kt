package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.StringBooleanListViewHolder
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract

val STRING_BOOLEAN_LIST_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = StringBooleanListViewHolder::class.java,
    layoutResId = R.layout.item_param_string_boolean_list,
    callbackClass = ParamsActionCallback::class.java,
)

data class StringBooleanListWrapperItem(
    val fields: FieldRealm,
    val options: List<OptionsRealm>,
) :
    ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = STRING_BOOLEAN_LIST_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = fields.id
    override fun areContentsTheSame(newItem: ListItem): Boolean = this == newItem
}
