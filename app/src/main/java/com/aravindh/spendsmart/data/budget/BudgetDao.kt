package com.aravindh.spendsmart.data.budget

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(budget: List<Budget>)

    @Insert
    suspend fun insert(budget: Budget)
}