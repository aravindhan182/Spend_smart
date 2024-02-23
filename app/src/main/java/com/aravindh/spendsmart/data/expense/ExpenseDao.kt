package com.aravindh.spendsmart.data.expense

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expenses: List<Expense>)

    @Insert
    suspend fun insert(expense: Expense)
}