package com.aravindh.spendsmart.data.budget

import androidx.room.Insert

interface BudgetDao {
    @Insert
    suspend fun insert(budget: List<Budget>)

    @Insert
    suspend fun insert(budget: Budget)
}