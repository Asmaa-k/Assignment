package com.asmaa.khb.filterapp.ui.filters.fragments

import androidx.lifecycle.lifecycleScope
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.ICON_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.LIST_NUMERIC_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.LIST_STRING_BOOLEAN_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.LIST_STRING_ICON_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.LIST_STRING_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.TEXT_TYPE
import com.asmaa.khb.filterapp.ui.filters.adapter.items.IconOrTextListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.NumericListWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.StringBooleanListWrapperItem
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


internal fun getParamsListItemWrappers(result: Map<FieldRealm, List<OptionsRealm>>): List<ListItem> =
    arrayListOf<ListItem>().apply {
        for ((field, options) in result) {
            when (field.dataType) {
                LIST_STRING_TYPE -> {
                    add(IconOrTextListWrapperItem(field, options, TEXT_TYPE))
                }

                LIST_STRING_ICON_TYPE -> {
                    add(IconOrTextListWrapperItem(field, options, ICON_TYPE))
                }

                LIST_NUMERIC_TYPE -> {
                    add(NumericListWrapperItem(field, options))
                }

                LIST_STRING_BOOLEAN_TYPE -> {
                    add(StringBooleanListWrapperItem(field, options))
                }
            }
        }
    }

internal fun ParamsFilterFragment.openOptionsDialogFragmentResult(
    fieldItem: IconOrTextListWrapperItem, selectedOptions: ArrayList<OptionsRealm>
) {
    lifecycleScope.launch {
        withContext(Dispatchers.Main) {
            fieldItem.options.map { option ->
                selectedOptions.find { it.id == option.id } ?: option
            }.toMutableList()
            updateAdapter(fieldItem)
        }
    }
}

internal fun ParamsFilterFragment.openOptionsPagerDialogFragmentResult(
    fieldItem: NumericListWrapperItem, selectedOptions: ArrayList<OptionsRealm>
) {
    lifecycleScope.launch {
        fieldItem.options.map { option ->
            selectedOptions.find { it.id == option.id } ?: option
        }.toMutableList()
        updateAdapter(fieldItem)
    }
}

internal fun ParamsFilterFragment.onSingleOptionSelectedResult(
    fieldItem: IconOrTextListWrapperItem, selectedOption: OptionsRealm
) {
    lifecycleScope.launch {
        fieldItem.options.map { option ->
            if (option.id == selectedOption.id) selectedOption else option
        }.toMutableList()
        updateAdapter(fieldItem)
    }
}

internal fun ParamsFilterFragment.onSingleOptionSelectedResult(
    field: StringBooleanListWrapperItem, selectedOption: OptionsRealm
) {
    lifecycleScope.launch {
        field.options.map { option ->
            if (option.id == selectedOption.id) selectedOption else option
        }.toMutableList()
        updateAdapter(field)
    }
}


internal fun ParamsFilterFragment.updateAdapter(listItem: ListItemWrapper) {
    mAdapter.updateItemData(listItem, true)
}