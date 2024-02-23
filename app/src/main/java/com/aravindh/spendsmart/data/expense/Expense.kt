package com.aravindh.spendsmart.data.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aravindh.spendsmart.data.budget.Category
import java.time.LocalDateTime

@Entity(tableName = "expense")
data class Expense(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "budgetId")
    val budgetId: Long,

    @ColumnInfo(name = "date")
    val date: LocalDateTime,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "category")
    var category: Category,

    @ColumnInfo(name = "account")
    var account: Account,

    @ColumnInfo(name = "notes")
    var notes: String
)

enum class Account {
    CASH,
    CARD,
    ACCOUNTS
}