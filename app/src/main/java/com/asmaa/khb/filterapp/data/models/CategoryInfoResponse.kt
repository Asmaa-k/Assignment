package com.asmaa.khb.filterapp.data.models

import com.google.gson.annotations.SerializedName

data class CategoryInfoResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("label_en")
    val label: String,
    @SerializedName("reporting_name")
    val reportingName: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("has_child")
    val hasChild: Int,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("subCategories")
    val subCategories: List<CategoryInfoResponse>,
)