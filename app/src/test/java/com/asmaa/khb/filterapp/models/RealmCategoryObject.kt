package com.asmaa.khb.filterapp.models
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class RealmCategoryObject(
    @PrimaryKey
    var id: Int = 0,
    var label: String = "",
    var reportingName : String = ""
): RealmObject {
    constructor() : this(0, "", "")
}
