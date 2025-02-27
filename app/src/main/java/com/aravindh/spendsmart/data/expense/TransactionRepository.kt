package com.aravindh.spendsmart.data.expense

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.aravindh.spendsmart.data.AppDatabase
import java.time.LocalDate

class TransactionRepository(private val dao: TransactionDao) {

    companion object {
        private var INSTANCE: TransactionRepository? = null

        @RequiresApi(Build.VERSION_CODES.O)
        fun getInstance(context: Context): TransactionRepository {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            synchronized(this) {
                val dao = AppDatabase.getInstance(context).expenseDao()
                val repository = TransactionRepository(dao)
                INSTANCE = repository
                return repository
            }
        }
    }

    suspend fun addTransactions(transactions: List<Transaction>) {
        dao.insert(transactions)
    }

    suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        dao.update(transaction)
    }

    fun getTransactions(): LiveData<List<Transaction>> {
        return dao.getTransactions()
    }

    fun getTodayExpense(today: LocalDate, tType: TransactionType): LiveData<Double?> {
        return dao.getTodayExpense(today, tType)
    }

    fun getTodayIncome(today: LocalDate, tType: TransactionType): LiveData<Double?> {
        return dao.getTodayExpense(today, tType)
    }

    suspend fun getTransactionDetailById(id: String): Transaction {
        return dao.getTransactionBtId(id)
    }

    suspend fun deleteById(id: String) {
        dao.deleteById(id)
    }

    fun getDataByYearMonth(yearMonth: String): LiveData<List<Transaction>> {
        return dao.getDataByYearMonth(yearMonth)
    }

    fun getDataByDateRange(fromDate: LocalDate, toDate: LocalDate): LiveData<List<Transaction>> {
        return dao.getDataByDateRange(fromDate, toDate)
    }

    fun getTodayExpenseTransaction(
        today: LocalDate,
        tType: TransactionType
    ): LiveData<List<Transaction>> {
        return dao.getTodayExpenseTransaction(today, tType)
    }

    fun getExpensesByDateRange(
        transactionType: TransactionType,
        fromDate: LocalDate,
        toDate: LocalDate
    ): LiveData<List<Transaction>> {
        return dao.getExpenseByDateRange(transactionType, fromDate, toDate)
    }
}