package com.aravindh.spendsmart.data.expense

import android.content.Context
import com.aravindh.spendsmart.data.AppDatabase

class ExpenseRepository(private val dao: ExpenseDao) {

    companion object {
        private var INSTANCE: ExpenseRepository? = null

        fun getInstance(context: Context): ExpenseRepository {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            synchronized(this) {
                val dao = AppDatabase.getInstance(context).expenseDao()
                val repository = ExpenseRepository(dao)
                INSTANCE = repository
                return repository
            }
        }
    }

    suspend fun addExpenses(expenses: List<Expense>) {
        dao.insert(expenses)
    }

    suspend fun addExpense(expense: Expense) {
        dao.insert(expense)
    }
}