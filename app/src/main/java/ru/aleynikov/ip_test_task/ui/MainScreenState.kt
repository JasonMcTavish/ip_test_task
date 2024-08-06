package ru.aleynikov.ip_test_task.ui

import ru.aleynikov.ip_test_task.domain.entity.Item


sealed class MainScreenState {

    object Initial : MainScreenState()

    data class Items(
        val items: List<Item>,
        val search: String
    ) : MainScreenState()
}
