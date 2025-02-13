package com.aravindh.spendsmart.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aravindh.spendsmart.convertors.DateConvertor
import com.aravindh.spendsmart.convertors.LocalTimeConverter
import com.aravindh.spendsmart.data.expense.TransactionDao
import com.aravindh.spendsmart.data.expense.Transaction

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [Transaction::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConvertor::class,LocalTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}