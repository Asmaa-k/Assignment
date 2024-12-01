package com.asmaa.khb.filterapp.domain.repositoriesImplementations

import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.data.local.SearchFlowRealm
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm
import com.asmaa.khb.filterapp.data.repositories.ICategoryAndSubCategoryFilterRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FilterRepository @Inject constructor(
    private val realm: Realm
) : ICategoryAndSubCategoryFilterRepository {

    //####################################################//
    //######### START CATEGORIES/SUB-CATEGORIES ########//
    //####################################################//
    override suspend fun retrieveAllCategories(): List<CategoryRealm>? {
        return withContext(Dispatchers.IO) {
            val result = realm.query<CategoryRealm>().find()
            result.ifEmpty { null }
        }
    }

    override suspend fun retrieveSubCategories(parentId: Int): List<SubCategoryRealm>? {
        return withContext(Dispatchers.IO) {
            val result = realm.query<SubCategoryRealm>("parentId == $0", parentId).find()
            result.ifEmpty { null }
        }
    }

    override suspend fun onSearchCategoriesItemsQuery(query: String): List<CategoryRealm>? {
        return withContext(Dispatchers.IO) {
            val result = realm.query<CategoryRealm>("label CONTAINS[c] $0", query).find()
            result.ifEmpty { null }
        }
    }

    override suspend fun onSearchSubCategoriesItemsQuery(query: String): List<SubCategoryRealm>? {
        return withContext(Dispatchers.IO) {
            val result = realm.query<SubCategoryRealm>("label CONTAINS[c] $0", query).find()
            result.ifEmpty { null }
        }
    }


    //####################################################//
    //######### START FIELDS-OPTIONS PARAM LOGIC ########//
    //####################################################//
    override suspend fun retrieveFieldsAndOptionsParams(categoryId: Int): Map<FieldRealm, List<OptionsRealm>> {
        return withContext(Dispatchers.IO) {
            val optionsMap = mutableMapOf<FieldRealm, List<OptionsRealm>>()
            val fieldsList = retrieveFieldsParams(categoryId) ?: return@withContext optionsMap

            // Iterate over the fields and fetch related options
            for (field in fieldsList) {
                val options =
                    realm.query<OptionsRealm>("fieldId == $0", field.id).find().copyFromRealm()
                optionsMap[field] = options
            }

            optionsMap
        }
    }

    private suspend fun retrieveFieldsParams(categoryId: Int): List<FieldRealm>? {
        return withContext(Dispatchers.IO) {
            val fieldsOrder = getOrderByCategoryId(categoryId)
            val results = realm.query<FieldRealm>("name IN { $fieldsOrder }").find().copyFromRealm()
            results.toList().ifEmpty { null }
        }
    }

    private fun getOrderByCategoryId(categoryId: Int): String? {
        val result =
            realm.query<SearchFlowRealm>("categoryId == $0", categoryId).find().firstOrNull()
        return result?.order?.ifEmpty { null }
    }
}
