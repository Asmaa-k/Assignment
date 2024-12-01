package com.asmaa.khb.filterapp.ui.filters.adapter.items

import com.asmaa.khb.filterapp.R
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.ParamsActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders.NumericListViewHolder
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import com.haizo.generaladapter.model.ViewHolderContract


val NUMERIC_LIST_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = NumericListViewHolder::class.java,
    layoutResId = R.layout.item_param_numeric_list,
    callbackClass = ParamsActionCallback::class.java,
)

data class NumericListWrapperItem(
    val fields: FieldRealm,
    val options: List<OptionsRealm>
) :
    ListItemWrapper {
    override val viewHolderContract: ViewHolderContract get() = NUMERIC_LIST_VIEW_HOLDER_CONTRACT
    override fun itemUniqueIdentifier(): String = fields.id
    override fun areContentsTheSame(newItem: ListItem): Boolean = this == newItem
}
