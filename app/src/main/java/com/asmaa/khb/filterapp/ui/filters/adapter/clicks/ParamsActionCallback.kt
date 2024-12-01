package com.asmaa.khb.filterapp.ui.filters.adapter.clicks

import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.NumericListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.StringBooleanListWrapperItem
import com.haizo.generaladapter.interfaces.BaseActionCallback

interface ParamsActionCallback : BaseActionCallback {
    fun openOptionsDialogFragment(field: IconOrTextListWrapperItem, fieldLabel: String)
    fun openOptionsPagerDialogFragment(field: NumericListWrapperItem, fieldLabel: String)
    fun onSingleOptionSelected(field: IconOrTextListWrapperItem, selectedOption: OptionsRealm)
    fun onSingleOptionSelected(field: StringBooleanListWrapperItem, selectedOption: OptionsRealm)
}