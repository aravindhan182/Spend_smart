package com.aravindh.spendsmart.data.income

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aravindh.spendsmart.data.expense.Account
import java.time.LocalDateTime

@Entity(tableName = "income")
data class Income(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "budgetId")
    val budgetId: Long,

    @ColumnInfo(name = "date")
    val date: LocalDateTime,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "incomeCategory")
    var incomeCategory: IncomeCategory,

    @ColumnInfo(name = "account")
    var account: Account,

    @ColumnInfo(name = "notes")
    var notes: String
)

enum class IncomeCategory {
    ALLOWANCE,
    SALARY,
    PETTY_CASH,
    OTHER
}