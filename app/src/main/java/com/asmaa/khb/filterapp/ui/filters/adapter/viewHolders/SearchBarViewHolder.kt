package com.asmaa.khb.filterapp.ui.filters.adapter.viewHolders

import android.widget.SearchView
import androidx.core.view.isVisible
import com.asmaa.khb.filterapp.databinding.ItemSearchBarBinding
import com.asmaa.khb.filterapp.ui.filters.adapter.clicks.CategoryAndSubCategoryActionCallback
import com.asmaa.khb.filterapp.ui.filters.adapter.items.SearchItemWrapper
import com.asmaa.khb.filterapp.ui.filters.adapter.items.SearchViewHolderExtras
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder


//####################################################//
//##################### VIEW HOLDER #######################//
//####################################################//
class SearchBarViewHolder(
    private val binding: ItemSearchBarBinding,
    private val callback: CategoryAndSubCategoryActionCallback,
    private val extras: SearchViewHolderExtras,
) : BaseBindingViewHolder<SearchItemWrapper>(binding, callback) {

    override fun onBind(listItem: SearchItemWrapper) {
        //setup main label
        binding.textCategoryHeader.isVisible = extras.categoryLabel.isNotEmpty()
        binding.textCategoryHeader.text = extras.categoryLabel

        //setup search-view
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                callback.onSearch(newText)
                return true
            }
        })
    }
}