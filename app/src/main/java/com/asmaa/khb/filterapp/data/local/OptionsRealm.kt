package com.asmaa.khb.filterapp.data.local

import android.os.Parcelable
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
class OptionsRealm(
    @PrimaryKey var id: String = "",
    var fieldId: String = "",
    var name: String = "",
    var value: String = "",
    var icon: String? = "",
    var order: String = "",
    var hasChild: String = "",
    var parentId: String? = "",
) : RealmObject, Parcelable {
    var isItemSelected: Boolean = false //handel-locally

    constructor() : this("", "", "", "", "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true // Same reference
        if (other == null || other::class != this::class) return false

        other as OptionsRealm

        return id == other.id &&
                fieldId == other.fieldId &&
                name == other.name &&
                value == other.value &&
                icon == other.icon &&
                order == other.order &&
                hasChild == other.hasChild &&
                parentId == other.parentId &&
                isItemSelected == other.isItemSelected
    }

    override fun hashCode(): Int {
        return listOf(
            id,
            fieldId,
            name,
            value,
            icon,
            order,
            hasChild,
            parentId,
            isItemSelected
        ).hashCode()
    }
}

