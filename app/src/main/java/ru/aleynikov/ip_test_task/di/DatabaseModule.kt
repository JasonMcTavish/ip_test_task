package ru.aleynikov.ip_test_task.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aleynikov.ip_test_task.data.ItemRepository
import ru.aleynikov.ip_test_task.data.ItemRepositoryImpl
import ru.aleynikov.ip_test_task.db.ItemDAO
import ru.aleynikov.ip_test_task.db.LocalDatabase

@InstallIn(SingletonComponent::class)
@Module
interface DatabaseModule {

    @Binds
    fun bindItemRepository(authImpl: ItemRepositoryImpl): ItemRepository

    companion object {

        @Provides
        fun providesDatabase(@ApplicationContext context: Context): LocalDatabase {
            return LocalDatabase.getInstance(context)
        }

        @Provides
        fun providesItemDAO(database: LocalDatabase): ItemDAO {
            return database.itemDAO()
        }
    }
}