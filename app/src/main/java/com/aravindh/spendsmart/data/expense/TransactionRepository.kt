package com.aravindh.spendsmart.data.expense

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.aravindh.spendsmart.data.AppDatabase

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

    fun getTransactions():LiveData<List<Transaction>> {
         return dao.getTransactions()
    }
}