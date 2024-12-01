package com.asmaa.khb.filterapp.data.repositories

import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm

interface ICategoryAndSubCategoryFilterRepository {

    suspend fun retrieveAllCategories(): List<CategoryRealm>?
    suspend fun retrieveSubCategories(parentId: Int): List<SubCategoryRealm>?
    suspend fun onSearchCategoriesItemsQuery(query: String): List<CategoryRealm>?
    suspend fun onSearchSubCategoriesItemsQuery(query: String): List<SubCategoryRealm>?
    suspend fun retrieveFieldsAndOptionsParams(categoryId: Int): Map<FieldRealm, List<OptionsRealm>>
}