package com.aravindh.spendsmart.data.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDate

@Dao
interface TransactionDao {

    @Insert
    suspend fun insert(expenses: List<Transaction>)

    @Insert
    suspend fun insert(expense: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Query("SELECT * FROM transactions")
    fun getTransactions(): LiveData<List<Transaction>>

    @Query("SELECT SUM(amount) FROM transactions where created_date = :today and transaction_type = :tType")
    fun getTodayExpense(today: LocalDate, tType: TransactionType): LiveData<Double?>

    @Query("SELECT SUM(amount) FROM transactions where created_date = :today and transaction_type = :tType")
    fun getTodayIncome(today: LocalDate, tType: TransactionType): LiveData<Double?>

    @Query("SELECT * FROM transactions WHERE id = :id")
   suspend fun getTransactionBtId(id: String): Transaction
}