package com.asmaa.khb.filterapp.data.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


open class CategoryRealm(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var label: String = "",
    var reportingName: String = "",
    var icon: String = "",
    var order: Int = 0,
    var hasChild: Int = 0,
) : RealmObject {
    constructor() : this(0, "", "", "", "", 0, 0)
}
