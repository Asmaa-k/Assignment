package com.asmaa.khb.filterapp.ui.filters.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asmaa.khb.filterapp.data.local.CategoryRealm
import com.asmaa.khb.filterapp.data.local.FieldRealm
import com.asmaa.khb.filterapp.data.local.OptionsRealm
import com.asmaa.khb.filterapp.data.local.SubCategoryRealm
import com.asmaa.khb.filterapp.domain.repositoriesImplementations.FilterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
internal class FilterViewModel @Inject constructor(
    private val repository: FilterRepository
) : ViewModel() {
    private val _categoryList: MutableSharedFlow<List<CategoryRealm>?> = MutableSharedFlow()
    internal val categoryList = _categoryList.asSharedFlow()

    private val _subcategoryList: MutableSharedFlow<List<SubCategoryRealm>?> = MutableSharedFlow()
    internal val subcategoryList = _subcategoryList.asSharedFlow()

    private val _fieldsOptionsPrams: MutableSharedFlow<Map<FieldRealm, List<OptionsRealm>>> =
        MutableSharedFlow()
    internal val fieldsOptionsPrams = _fieldsOptionsPrams.asSharedFlow()

    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    internal val loading = _loading.asSharedFlow()

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    internal val error = _error.asSharedFlow()

    private var debounceJob: Job? = null


    fun getLocalList(categoryId: Int) {
        viewModelScope.launch {
            _loading.emit(true)
            try {
                if (categoryId.hasParent()) {
                    val subCategories = repository.retrieveSubCategories(categoryId)
                    _subcategoryList.emit(subCategories)
                } else {
                    val categories = repository.retrieveAllCategories()
                    _categoryList.emit(categories)
                }

            } catch (exception: Exception) {
                _error.emit("An error occurred: ${exception.message}")
            }
            _loading.emit(false)
        }
    }

    fun onSearchQuery(categoryId: Int, query: String) {
        debounceJob?.cancel()
        debounceJob = CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            if (categoryId.hasParent()) {
                val data = repository.onSearchSubCategoriesItemsQuery(query)
                _subcategoryList.emit(data)
            } else {
                val data = repository.onSearchCategoriesItemsQuery(query)
                _categoryList.emit(data)
            }
        }
    }

    //it specify if what I have is category or sub-category
    private fun Int.hasParent(): Boolean = this != 0

    fun getDynamicParamsLocal(subcategoryId: Int) {
        viewModelScope.launch {
            _loading.emit(true)
            try {
                val results = repository.retrieveFieldsAndOptionsParams(subcategoryId)
                _fieldsOptionsPrams.emit(results)
            } catch (exception: Exception) {
                _error.emit("An error occurred: ${exception.message}")
            }
            _loading.emit(false)
        }
    }
}
