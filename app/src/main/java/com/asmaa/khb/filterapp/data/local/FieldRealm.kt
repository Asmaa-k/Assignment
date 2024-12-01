package com.asmaa.khb.filterapp.data.local

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class FieldRealm(
    @PrimaryKey var id: String = "",
    var name: String = "",
    var label: String = "",
    var dataType: String = "",
    var parentName: String? = "",
    var parentId: Int = 0,
) : RealmObject {
    constructor() : this("", "", "", "", "", 0)


    override fun equals(other: Any?): Boolean {
        // Check for the same reference
        if (this === other) return true
        // Check for null or class type mismatch
        if (other == null || other::class != this::class) return false
        // Check if the fields are the same
        other as FieldRealm
        return id == other.id &&
                name == other.name &&
                label == other.label &&
                dataType == other.dataType &&
                parentName == other.parentName &&
                parentId == other.parentId
    }

    override fun hashCode(): Int {
        // Use a combination of all fields to generate the hash code
        return 31 * id.hashCode() +
                31 * name.hashCode() +
                31 * label.hashCode() +
                31 * dataType.hashCode() +
                31 * (parentName?.hashCode() ?: 0) +
                31 * parentId
    }
}


