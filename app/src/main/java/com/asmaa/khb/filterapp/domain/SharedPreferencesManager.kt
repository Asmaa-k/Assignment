package com.asmaa.khb.filterapp.domain

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val PREF_NAME = "HashPreferences"
        const val HASH_CATEGORIES_KEY = "hash_categories"
        const val HASH_DYNAMIC_ATTRIBUTES_KEY = "hash_dynamic_attributes"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveHash(hashKey: String, value: String) {
        sharedPreferences.edit().putString(hashKey, value).apply()
    }

    fun getHash(hashKey: String): String? {
        return sharedPreferences.getString(hashKey, "")
    }
}
