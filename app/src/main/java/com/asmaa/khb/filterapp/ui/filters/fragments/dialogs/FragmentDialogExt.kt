package com.asmaa.khb.filterapp.ui.filters.fragments.dialogs

import android.app.Dialog
import android.view.ViewGroup
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.items.OptionListViewHolderExtras
import com.asmaa.khb.filterapp.ui.filters.adapter.items.OptionsItemWrapper
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

lateinit var mOnOptionResult: (item: OptionsRealm) -> Unit
lateinit var mOnOptionsResult: (list: ArrayList<OptionsRealm>) -> Unit

internal val selectedOption = arrayListOf<OptionsRealm>()
private var searchJob: Job? = null

internal fun BlenderListAdapter.setupAdapter(
    options: ArrayList<OptionsRealm>, showNavIcon: Boolean
) {
    this.setExtraParams(OptionListViewHolderExtras(showNavIcon))
    val list = options.map { OptionsItemWrapper(it) }
    this.submitList(list)
}

internal fun BlenderListAdapter.updateOption(option: OptionsRealm) {
    val listItem = OptionsItemWrapper(option)
    this.updateItemData(listItem, true)
}

internal fun Dialog?.setDialogLayoutParams() {
    this?.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
}

internal fun filterOptions(
    query: String, options: ArrayList<OptionsRealm>, mAdapter: BlenderListAdapter
) {
    searchJob?.cancel()
    searchJob = CoroutineScope(Dispatchers.Main).launch {
        delay(300)
        val filteredList = options.filter {
            it.value.contains(query, ignoreCase = true)
        }

        val list = filteredList.map { OptionsItemWrapper(it) }
        mAdapter.submitList(list)
    }
}

fun restAllItems(mAdapter: BlenderListAdapter) {
    selectedOption.forEach {
        it.isItemSelected = false
        mAdapter.updateOption(it)
    }
    selectedOption.clear()
}

fun OptionsPagerDialogFragment.setupPagerAction(option: OptionsRealm) {
    selectedOption.add(option)
    if (selectedOption.size >= 2) {
        mOnOptionsResult.invoke(selectedOption)
        dismiss()
    }
    val currentItem = binding.viewPager.currentItem
    val nextItem = if (currentItem == pagerAdapter.itemCount - 1) 0 else currentItem + 1

    binding.viewPager.setCurrentItem(nextItem, true)
}
