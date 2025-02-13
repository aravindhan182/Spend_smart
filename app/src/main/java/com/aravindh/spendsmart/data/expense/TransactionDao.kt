package com.aravindh.spendsmart.data.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(expenses: List<Transaction>)

    @Insert
    suspend fun insert(expense: Transaction)

    @Query("SELECT * FROM transactions")
    fun getTransactions(): LiveData<List<Transaction>>
}