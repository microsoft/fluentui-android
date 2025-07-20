import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import java.util.UUID

/**
 * Remembers a unique identifier for a composable.
 *
 * If a non-null [id] is provided, it will be returned.
 * If [id] is null, a new unique ID will be generated using UUID and
 * remembered across recompositions and process death.
 *
 * @param id An optional existing ID of type [Any].
 * @return The provided [id] or a newly generated unique ID.
 */
fun rememberUniqueId(id: Any? = null): Any {
    return id ?: { UUID.randomUUID().toString() }
}

/**
 * An interface for objects that can be searched.
 * Any class implementing this interface must provide a string key to be used for filtering.
 */
interface Searchable {
    /**
     * @return The string value that will be checked against the search query.
     */
    fun getSearchKey(): String
    /**
     * @return A unique identifier for this item, used for stable list updates in Compose.
     */
    fun getUniqueId(): Any
}

/**
 * A generic UI state holder for the search screen.
 * @param T The type of item being searched.
 */
data class SearchUiState<T>(
    val searchQuery: String = "",
    val filteredItems: List<T> = emptyList(),
    val selectedItems: Set<T> = emptySet(),
    val selectionSize: Int = 0
)


/**
 * A generic ViewModel to handle search logic for any list of 'Searchable' items.
 *
 * @param T The type of item being searched, constrained to implement [Searchable].
 * @param initialItems The initial list of items to be displayed and searched.
 */
class SearchViewModel<T : Searchable>(
    initialItems: List<T>
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    private val _allItems = MutableStateFlow(initialItems)  // CAN CONSIDER USING IMMUTABLE TYPES FOR STABLE UPDATES AND PERFORMANCE
    private val _selectedItems = MutableStateFlow<Set<T>>(emptySet())

    val uiState: StateFlow<SearchUiState<T>> =
        combine(_searchQuery, _allItems, _selectedItems) { query, items, selected ->
            val itemsToShow = if (query.isBlank()) {
                items
            } else {
                items.filter { item ->
                    item.getSearchKey().contains(query, ignoreCase = true)
                }
            }
            SearchUiState(
                searchQuery = query,
                filteredItems = itemsToShow,
                selectedItems = selected,
                selectionSize = selected.size
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState(filteredItems = initialItems)
        )

    /** Handles the query change event from the UI. */
    fun onQueryChanged(query: String) {
        _searchQuery.value = query
    }

    /** Adds a new item to the master list. */
    fun addItem(item: T) {
        _allItems.update { currentList -> currentList + item }
    }

    /** Removes an item from the master list using its unique ID. */
    fun removeItem(item: T) {
        _selectedItems.update { it - item }
        _allItems.update { currentList ->
            currentList.filter { it.getUniqueId() != item.getUniqueId() }
        }
    }

    /**
     * Adds a single item to the selection set.
     */
    fun selectItem(item: T) {
        _selectedItems.update { currentSet ->
            // The '+' operator on a set creates a new set with the item added
            currentSet + item
        }
    }

    /**
     * Removes a single item from the selection set.
     */
    fun deselectItem(item: T) {
        _selectedItems.update { currentSet ->
            // The '-' operator on a set creates a new set with the item removed
            currentSet - item
        }
    }

    /**
     * A more convenient function for UI toggles. Selects an item if it's not
     * selected, and deselects it if it is already selected.
     */
    fun toggleSelection(item: T) {
        _selectedItems.update { currentSet ->
            if (item in currentSet) {
                currentSet - item
            } else {
                currentSet + item
            }
        }
    }
}

/**
 * A factory for creating instances of [SearchViewModel] with parameters.
 */
class SearchViewModelFactory<T : Searchable>(
    private val initialItems: List<T>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(initialItems) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}