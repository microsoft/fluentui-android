package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 1. A proper data class. `equals()` and `hashCode()` are generated automatically.
// This is CRITICAL for `contains()` and `remove()` to work correctly.
// I've added a unique ID, which is a best practice for lists.
data class SearchItemUiModel(
    val id: String, // A unique identifier is crucial
    val title: String,
    val subTitle: String? = null,
    val footer: String? = null
    // UI-specific things like accessories can be decided in the Composable
)

class SearchViewModel : ViewModel() {

    // The single source of truth for all possible items (e.g., loaded from a repository)
    private val allItems = listOf(
        SearchItemUiModel("1", "Ana Bowman", "UX Designer"),
        SearchItemUiModel("2", "John Doe", "Software Engineer"),
        SearchItemUiModel("3", "Jane Smith", "Product Manager"),
        SearchItemUiModel("4", "Peter Jones", "QA Tester"),
        SearchItemUiModel("5", "Mary Johnson", "DevOps Specialist"),
        SearchItemUiModel("6", "Chris Lee", "Data Scientist")
    )

    // A coroutine job for search, so we can cancel it if the user types again quickly.
    private var searchJob: Job? = null

    // --- STATE ---

    // State for the search query.
    var searchQuery by mutableStateOf("")
        private set // Only ViewModel can change this

    // State for the filtered list of items displayed to the user.
    var filteredItems by mutableStateOf(allItems)
        private set

    // State for loading indicator.
    var isLoading by mutableStateOf(false)
        private set

    // STATE FOR THE LIST: The single source of truth for selected items.
    // We use mutableStateListOf for efficient recompositions on list changes.
    val selectedItems = mutableStateListOf<SearchItemUiModel>()

    // DERIVED STATE: The UI can observe this to know when to show the count.
    val isSelectionModeActive by derivedStateOf { selectedItems.isNotEmpty() }

    // --- EVENTS ---

    /**
     * Called when the user types in the search bar.
     */
    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        searchJob?.cancel() // Cancel previous search coroutine
        searchJob = viewModelScope.launch {
            isLoading = true
            delay(300) // Debounce: wait for user to stop typing
            filteredItems = if (query.isBlank()) {
                allItems
            } else {
                allItems.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.subTitle?.contains(query, ignoreCase = true) == true
                }
            }
            isLoading = false
        }
    }

    /**
     * Toggles the selection status of an item.
     * This is the core logic you requested.
     */
    fun toggleSelection(item: SearchItemUiModel) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
    }

    /**
     * Called when the "Clear" button is pressed.
     */
    fun clearSelection() {
        selectedItems.clear()
    }

    /**
     * Called when the "Apply" button is pressed.
     * (Add your logic here, e.g., pass the list back to a repository or previous screen)
     */
    fun applySelection() {
        // TODO: Implement what happens when you apply the selection
        println("Applied selection: ${selectedItems.joinToString { it.title }}")
    }
}