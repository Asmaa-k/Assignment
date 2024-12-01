package com.asmaa.khb.filterapp.ui.filters.util

import android.content.Context
import android.util.TypedValue
import java.util.UUID

object Util {
    fun Int.toDp(context: Context): Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()


    fun generateUUID(): String {
        return UUID.randomUUID().toString()
    }
}