package com.aravindh.spendsmart.data.budget

import android.content.Context
import com.aravindh.spendsmart.data.AppDatabase

class BudgetRepository(private val dao: BudgetDao) {

    companion object {
        private var INSTANCE: BudgetRepository? = null

        fun getInstance(context: Context): BudgetRepository {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            synchronized(this) {
                val dao = AppDatabase.getInstance(context).budgetDao()
                val repository = BudgetRepository(dao)
                INSTANCE = repository
                return repository
            }
        }
    }

    suspend fun addBudgets(budgets: List<Budget>) {
        dao.insert(budgets)
    }

    suspend fun addBudget(budget: Budget) {
        dao.insert(budget)
    }
}