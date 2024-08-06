package ru.aleynikov.ip_test_task.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.aleynikov.ip_test_task.data.ItemRepository
import ru.aleynikov.ip_test_task.domain.entity.Item
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: ItemRepository) :
    ViewModel() {

    private val _searchText = MutableStateFlow("")

    val mainStateFlow: StateFlow<MainScreenState> = combine(
        repository.itemList,
        _searchText
    ) { items, query ->
        if (query.isEmpty()) {
            MainScreenState.Items(items, query)
        } else {
            val filteredItems = items.filter {
                it.name.contains(query, ignoreCase = true)
            }
            MainScreenState.Items(filteredItems,query)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = MainScreenState.Initial
        )

    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
    }

    fun onAmountChange(item: Item, amount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeAmount(item.copy(amount = amount))
        }
    }

    fun onDeleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(item)
        }
    }


}