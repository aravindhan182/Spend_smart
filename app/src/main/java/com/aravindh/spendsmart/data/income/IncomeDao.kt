package com.aravindh.spendsmart.data.income

import androidx.room.Insert

interface IncomeDao {

    @Insert
    suspend fun insert(incomes: List<Income>)

    @Insert
    suspend fun insert(income: Income)
}