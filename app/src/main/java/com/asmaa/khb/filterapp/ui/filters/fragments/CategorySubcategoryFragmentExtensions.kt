package com.asmaa.khb.filterapp.ui.filters.fragments

import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm
import com.asmaa.khb.filterapp.ui.filters.adapter.items.CategoryWrapperItem
import com.asmaa.khb.filterapp.ui.filters.adapter.items.SearchItemWrapper
import com.asmaa.khb.filterapp.ui.filters.adapter.items.SearchViewHolderExtras
import com.asmaa.khb.filterapp.ui.filters.adapter.items.SubCategoryWrapperItem
import com.haizo.generaladapter.model.ListItem

internal fun getCategoryListItem(result: List<CategoryRealm>?): List<ListItem> =
    arrayListOf<ListItem>().apply {
        /* ADD HEADER */
        add(0, SearchItemWrapper())

        /* ADD CATEGORY LIST */
        result?.let {
            val wrapperItems = result.map { CategoryWrapperItem(it) }
            addAll(wrapperItems)
        }
    }

internal fun getSubCategoryListItem(result: List<SubCategoryRealm>?): List<ListItem> =
    arrayListOf<ListItem>().apply {
        /* ADD HEADER */
        add(0, SearchItemWrapper())

        /* ADD SUB-CATEGORY LIST */
        result?.let {
            val wrapperItems = result.map { SubCategoryWrapperItem(it) }
            addAll(wrapperItems)
        }
    }


internal fun CategorySubcategoryFragment.navigateToSubCategoryScreen(
    categoryId: Int, label: String
) {
    val action = CategorySubcategoryFragmentDirections.actionCategoryToSubcategoryFragment(
        categoryId = categoryId,
        categoryLabel = label
    )

    navController.navigate(action)
}


internal fun CategorySubcategoryFragment.navigateToParamsScreen(subcategoryId: Int) {
    val action = CategorySubcategoryFragmentDirections.actionCategoryFragmentToParamsFilterFragment(
        subcategoryId = subcategoryId,
    )

    navController.navigate(action)
}

internal fun CategorySubcategoryFragment.getAdapterExtra(): SearchViewHolderExtras =
    SearchViewHolderExtras(args.categoryLabel ?: "")


