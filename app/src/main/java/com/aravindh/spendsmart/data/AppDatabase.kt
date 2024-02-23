package com.aravindh.spendsmart.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aravindh.spendsmart.adapter.DateTimeAdapter
import com.aravindh.spendsmart.data.budget.Budget
import com.aravindh.spendsmart.data.budget.BudgetDao
import com.aravindh.spendsmart.data.expense.Expense
import com.aravindh.spendsmart.data.expense.ExpenseDao
import com.aravindh.spendsmart.data.income.Income
import com.aravindh.spendsmart.data.income.IncomeDao

@RequiresApi(Build.VERSION_CODES.O)
@Database(
    entities = [Budget::class, Income::class, Expense::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateTimeAdapter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao

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