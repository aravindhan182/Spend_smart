package com.aravindh.spendsmart.data.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aravindh.spendsmart.data.income.TransactionType
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "expense")
data class Expense(

    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "created_date")
    val createdDate: LocalDate,

    @ColumnInfo(name = "created_time")
    val createdTime: LocalTime,

    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "category")
    var expenseCategory: ExpenseCategory,

    @ColumnInfo(name = "payment_method")
    var paymentMethod: PaymentMethod,

    @ColumnInfo(name = "notes")
    var notes: String
)

enum class ExpenseCategory(val code: Int, val value: String) {
    BEAUTY(1, "Beauty"),
    VEHICLE(1, "Vehicle"),
    CLOTHING(1, "Clothing"),
    EDUCATION(1, "Education"),
    HOME(1, "Home"),
    FOOD(1, "Food"),
    TRANSPORT(1, "Transport"),
    SHOPPING(1, "Shopping"),
    ENTERTAINMENT(1, "Entertainment"),
    INSURANCE(1, "Insurance"),
    RECHARGE(1, "Recharge"),
}

enum class PaymentMethod(val code: Int, val value: String) {
    CASH(1, "Cash"),
    CARD(2, "Card"),
    ACCOUNTS(3, "Accounts")
}