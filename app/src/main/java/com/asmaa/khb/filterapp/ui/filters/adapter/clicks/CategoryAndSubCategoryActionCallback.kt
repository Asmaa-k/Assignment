package com.asmaa.khb.filterapp.ui.filters.adapter.clicks

import com.haizo.generaladapter.interfaces.BaseActionCallback

interface CategoryAndSubCategoryActionCallback : BaseActionCallback {
    fun onCategoryClick(categoryId: Int, label: String)
    fun onSubCategoryClick(subCategory: Int)
    fun onSearch(query: String)
}