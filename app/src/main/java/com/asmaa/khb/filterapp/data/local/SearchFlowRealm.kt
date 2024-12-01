package com.asmaa.khb.filterapp.data.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


open class SearchFlowRealm(
    @PrimaryKey var categoryId: Int = 0,
    var order: String = ""
) : RealmObject {
    constructor() : this(0, "")
}

