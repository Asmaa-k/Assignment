package com.asmaa.khb.filterapp.models

import com.google.gson.annotations.SerializedName

data class CategoryObject(
    @SerializedName("id")
    val id: Int,
    @SerializedName("label_en")
    val label: String,
    @SerializedName("reporting_name")
    val reportingName: String,
)