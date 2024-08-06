package ru.aleynikov.ip_test_task.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.aleynikov.ip_test_task.db.ItemDAO
import ru.aleynikov.ip_test_task.db.toEntity
import ru.aleynikov.ip_test_task.db.toModel
import ru.aleynikov.ip_test_task.domain.entity.Item
import javax.inject.Inject

interface ItemRepository  {

    val itemList: Flow<List<Item>>

    fun changeAmount(goods: Item)

    fun deleteItem(goods: Item)
}

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDAO
) : ItemRepository {
    override val itemList: Flow<List<Item>>
        get() = itemDao.getItems().map { it.map { it.toEntity() } }

    override fun changeAmount(item: Item) {
        itemDao.updateItem(item.toModel())
    }

    override fun deleteItem(item: Item) {
        itemDao.deleteItem(item.toModel())
    }
}
