package com.example.appcontentprovider.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.appcontentprovider.dao.product.ProductDB
import com.example.appcontentprovider.dao.product.ProductDao
import com.example.appcontentprovider.provider.user.UserDao
import com.example.appcontentprovider.ui.user.UserDB

@Database(entities = [UserDB::class, ProductDB::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN cityName TEXT NOT NULL DEFAULT 'Uknown'")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).addMigrations(MIGRATION_1_2).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}