package com.aravindh.spendsmart.data.income

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.aravindh.spendsmart.data.AppDatabase
import com.aravindh.spendsmart.data.expense.Expense
import com.aravindh.spendsmart.data.expense.ExpenseDao

class IncomeRepository(private val dao: IncomeDao) {

    companion object {
        private var INSTANCE: IncomeRepository? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun getInstance(context: Context): IncomeRepository {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            synchronized(this) {
                val dao = AppDatabase.getInstance(context).incomeDao()
                val repository = IncomeRepository(dao)
                INSTANCE = repository
                return repository
            }
        }
    }

    suspend fun addIncomes(incomes: List<Income>) {
        dao.insert(incomes)
    }

    suspend fun addIncome(income: Income) {
        dao.insert(income)
    }
}