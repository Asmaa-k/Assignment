package com.asmaa.khb.filterapp.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val success: Boolean,
    val result: ResultResponse<T>
)

data class ResultResponse<T>(
    val status: Int,
    val hash: String,
    val data: T
)

data class CategoryDataItems(
    val items: List<CategoryInfoResponse>
)

data class DynamicAttributesAndOptionsData(
    @SerializedName("fields")
    val fieldsList: List<FieldInfoResponse>,
    @SerializedName("options")
    val optionsList: List<OptionInfoResponse>,
)

data class DynamicAttributesAssignRawData(
    @SerializedName("search_flow")
    val searchFlowList: List<SearchFlowResponse>,
    @SerializedName("fields_label")
    val fieldsLabelList: List<FieldsLabelResponse>,
)
