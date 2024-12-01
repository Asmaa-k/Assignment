package com.asmaa.khb.filterapp.domain.helpers

import android.content.Context
import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.data.local.SearchFlowRealm
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm
import com.asmaa.khb.filterapp.data.models.ApiResponse
import com.asmaa.khb.filterapp.data.models.CategoryDataItems
import com.asmaa.khb.filterapp.data.models.CategoryInfoResponse
import com.asmaa.khb.filterapp.data.models.DynamicAttributesAndOptionsData
import com.asmaa.khb.filterapp.data.models.DynamicAttributesAssignRawData
import com.asmaa.khb.filterapp.data.models.FieldInfoResponse
import com.asmaa.khb.filterapp.data.models.FieldsLabelResponse
import com.asmaa.khb.filterapp.data.models.OptionInfoResponse
import com.asmaa.khb.filterapp.data.models.SearchFlowResponse
import com.asmaa.khb.filterapp.domain.Constants
import com.asmaa.khb.filterapp.domain.SharedPreferencesManager
import com.asmaa.khb.filterapp.domain.SharedPreferencesManager.Companion.HASH_CATEGORIES_KEY
import com.asmaa.khb.filterapp.domain.SharedPreferencesManager.Companion.HASH_DYNAMIC_ATTRIBUTES_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LocalDataHelper @Inject constructor(
    private val context: Context,
    private val realm: Realm,
    private val sharedPrefManager: SharedPreferencesManager
) {


    //####################################################//
    //#### START SAVING DATA IF POSSIBLE "if hash-code didn't change"####//
    //####################################################//
    suspend fun saveAllCategoriesAndSubCategoriesIfPossible() {
        withContext(Dispatchers.IO) {
            val jsonFile =
                loadJSONFromAsset(Constants.CATEGORIES_AND_SUCATEGORIES_ASSET_FILE_NAME)
                    ?: throw Exception("Failed to load data from assets")
            val responseResults = parseCategoryItemsJson(jsonFile)
            if (isHashKeyChanged(responseResults.second, HASH_CATEGORIES_KEY)) {
                saveCategoryAndSubCategoryResponseToLocal(responseResults.first)
                saveHashKey(responseResults.second, HASH_CATEGORIES_KEY)
            }
        }
    }

    suspend fun saveAllDynamicAttributesIfPossible() {
        withContext(Dispatchers.IO) {
            //Start get search-flow and fields label from assets
            val assignRawJsonFile = loadJSONFromAsset(Constants.DYNAMIC_ATTRIBUTES_ASSIGN_RAW)
                ?: throw Exception("Failed to load data from assets")

            val assignRawResponseResults =
                parseDynamicAttributesAssignRawItemsJson(assignRawJsonFile)
            //end get search-flow and fields label from assets

            if (isHashKeyChanged(
                    assignRawResponseResults.second, HASH_DYNAMIC_ATTRIBUTES_KEY
                )
            ) {

                //Start get fields and options from asset
                val fieldsAndOptionsJsonFile =
                    loadJSONFromAsset(Constants.DYNAMIC_ATTRIBUTES_AND_OPTIONS_RAW)
                        ?: throw Exception("Failed to load data from assets")

                val fieldsAndOptionsResponseResults =
                    parseDynamicAttributesAndOptionsItemsJson(fieldsAndOptionsJsonFile)
                //end get fields and options from asset

                saveDynamicAttributesResponseToLocal(
                    Pair(
                        assignRawResponseResults.first,
                        fieldsAndOptionsResponseResults
                    )
                )

                saveHashKey(assignRawResponseResults.second, HASH_DYNAMIC_ATTRIBUTES_KEY)
            }
        }
    }


    //####################################################//
//############ LOAD DATA FROM ASSET FILE ############//
//####################################################//
    private suspend fun loadJSONFromAsset(fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (e: IOException) {
                null
            }
        }
    }


    //####################################################//
//### get/save hash's assuming we getting data from api-side ###//
//####################################################//
    private fun saveHashKey(hashKey: String, prefKey: String) =
        sharedPrefManager.saveHash(prefKey, hashKey)

    private fun isHashKeyChanged(apiHash: String, prefKey: String): Boolean =
        sharedPrefManager.getHash(prefKey) != apiHash

    //####################################################//
//############ PARSE JSON FILES TO CLASSES ############//
//####################################################//
    private fun parseCategoryItemsJson(jsonString: String): Pair<List<CategoryInfoResponse>, String> {
        val gson = Gson()
        val apiResponseType = object : TypeToken<ApiResponse<CategoryDataItems>>() {}.type
        val apiResponse: ApiResponse<CategoryDataItems> = gson.fromJson(jsonString, apiResponseType)

        return Pair(apiResponse.result.data.items, apiResponse.result.hash)
    }

    private fun parseDynamicAttributesAndOptionsItemsJson(jsonString: String): DynamicAttributesAndOptionsData {
        val gson = Gson()
        val apiResponseType =
            object : TypeToken<ApiResponse<DynamicAttributesAndOptionsData>>() {}.type
        val apiResponse: ApiResponse<DynamicAttributesAndOptionsData> =
            gson.fromJson(jsonString, apiResponseType)

        return apiResponse.result.data
    }

    private fun parseDynamicAttributesAssignRawItemsJson(jsonString: String): Pair<DynamicAttributesAssignRawData, String> {
        val gson = Gson()
        val apiResponseType =
            object : TypeToken<ApiResponse<DynamicAttributesAssignRawData>>() {}.type
        val apiResponse: ApiResponse<DynamicAttributesAssignRawData> =
            gson.fromJson(jsonString, apiResponseType)

        return Pair(apiResponse.result.data, apiResponse.result.hash)
    }

    //####################################################//
//## MAPPING AND SAVING RESPONSE TO LOCAL DB :CATEGORY-SUBCATEGORY ##//
//####################################################//
    private suspend fun saveCategoryAndSubCategoryResponseToLocal(items: List<CategoryInfoResponse>?) {
        withContext(Dispatchers.IO) {
            realm.write {
                this.deleteAll()
                items?.forEach { item ->
                    saveCategoryWithSubcategories(item)
                }
            }
        }
    }

    //save a single category and its subcategories
    private fun MutableRealm.saveCategoryWithSubcategories(item: CategoryInfoResponse) {
        val categoryRealmObj = mapCategoryToRealm(item)
        this.copyToRealm(categoryRealmObj)

        item.subCategories.forEach { subCat ->
            val subCategoryRealmObj = mapSubCategoryToRealm(subCat)
            this.copyToRealm(subCategoryRealmObj)
        }
    }

    // Mapper to convert CategoryInfoResponse to CategoryRealm
    private fun mapCategoryToRealm(item: CategoryInfoResponse) = CategoryRealm(
        id = item.id,
        name = item.name,
        label = item.label,
        reportingName = item.reportingName,
        icon = item.icon,
        order = item.order,
        hasChild = item.hasChild,
    )

    // Mapper to convert SubCategoryInfoResponse to SubCategoryRealm
    private fun mapSubCategoryToRealm(subCat: CategoryInfoResponse) = SubCategoryRealm(
        id = subCat.id,
        name = subCat.name,
        label = subCat.label,
        reportingName = subCat.reportingName,
        icon = subCat.icon,
        order = subCat.order,
        hasChild = subCat.hasChild,
        parentId = subCat.parentId
    )

    //####################################################//
//## MAPPING AND SAVING RESPONSE TO LOCAL DB : DYNAMIC-ATTRIBUTES ##//
//####################################################//
    private suspend fun saveDynamicAttributesResponseToLocal(
        responseResults: Pair<DynamicAttributesAssignRawData, DynamicAttributesAndOptionsData>
    ) {
        withContext(Dispatchers.IO) {
            realm.write {
                saveSearchFlowLists(responseResults.first.searchFlowList)
                saveOptionsLists(responseResults.second.optionsList)
                saveFieldsLists(
                    responseResults.first.fieldsLabelList,
                    responseResults.second.fieldsList
                )
            }
        }
    }


    private fun MutableRealm.saveSearchFlowLists(items: List<SearchFlowResponse>) {
        items.forEach { item ->
            val flowRealmObj = mapSearchFlowResponseToRealm(item)
            this.copyToRealm(flowRealmObj, UpdatePolicy.ALL)
        }
    }

    private fun mapSearchFlowResponseToRealm(item: SearchFlowResponse) = SearchFlowRealm(
        categoryId = item.categoryId,
        order = item.order.joinToString(",") { "\"$it\"" }
    )

    private fun MutableRealm.saveOptionsLists(items: List<OptionInfoResponse>) {
        items.forEach { item ->
            val flowRealmObj = mapOptionsResponseToRealm(item)
            this.copyToRealm(flowRealmObj, UpdatePolicy.ALL)
        }
    }

    private fun mapOptionsResponseToRealm(item: OptionInfoResponse) = OptionsRealm(
        id = item.id,
        fieldId = item.fieldId,
        name = item.name,
        value = item.value,
        icon = item.icon,
        order = item.order,
        hasChild = item.hasChild,
        parentId = item.parentId,
    )

    private fun MutableRealm.saveFieldsLists(
        fieldsLabelList: List<FieldsLabelResponse>?,
        fieldsList: List<FieldInfoResponse>
    ) {
        fieldsList.forEach { item ->
            val fieldRealmObj = mapFieldResponseToRealm(item, fieldsLabelList)
            this.copyToRealm(fieldRealmObj, UpdatePolicy.ALL)
        }
    }

    private fun mapFieldResponseToRealm(
        item: FieldInfoResponse,
        fieldsLabelList: List<FieldsLabelResponse>?
    ): FieldRealm = FieldRealm(
        id = item.id,
        name = item.name,
        label = getFieldLabel(item.name, fieldsLabelList),
        dataType = item.dataType,
        parentName = item.parentName,
        parentId = item.parentId,
    )

    private fun getFieldLabel(
        name: String,
        fieldsLabelList: List<FieldsLabelResponse>?
    ): String = fieldsLabelList?.find { it.fieldName == name }?.label ?: name
}