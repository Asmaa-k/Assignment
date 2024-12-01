package com.asmaa.khb.filterapp.data.models

import com.google.gson.annotations.SerializedName

data class FieldInfoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("data_type") val dataType: String,
    @SerializedName("parent_name") val parentName: String,
    @SerializedName("parent_id") val parentId: Int,
)

data class OptionInfoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("field_id") val fieldId: String,
    @SerializedName("label") val name: String,
    @SerializedName("value") val value: String,
    @SerializedName("option_img") val icon: String,
    @SerializedName("order") val order: String,
    @SerializedName("has_child") val hasChild: String,
    @SerializedName("parent_id") val parentId: String,
)

data class SearchFlowResponse(
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("order") val order: List<String>,
)

data class FieldsLabelResponse(
    @SerializedName("field_name") val fieldName: String,
    @SerializedName("label_ar") val label: String,
)


