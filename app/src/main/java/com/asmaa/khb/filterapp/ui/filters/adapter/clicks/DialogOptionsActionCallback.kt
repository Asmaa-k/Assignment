package com.asmaa.khb.filterapp.ui.filters.adapter.clicks

import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.haizo.generaladapter.interfaces.BaseActionCallback

interface DialogOptionsActionCallback : BaseActionCallback {
    fun onOptionSelected(isChecked: Boolean, optionsRealm: OptionsRealm)
}