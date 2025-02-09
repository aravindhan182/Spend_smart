package com.aravindh.spendsmart.data.income

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "income")
data class Income(

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

    @ColumnInfo(name = "income_category")
    var incomeCategory: IncomeCategory,

    @ColumnInfo(name = "notes")
    var notes: String
)

enum class TransactionType(val code: Int, var value: String) {
    INCOME(1, "INCOME"),
    EXPENSE(2, "EXPENSE")
}


enum class IncomeCategory(val code: Int, val value: String) {
    ALLOWANCE(1, "Allowance"),
    SALARY(2, "Salary"),
    CRYPTO(3, "Crypto"),
    OTHERS(4, "Others"),
    AWARDS(4, "Awards"),
    COUPONS(4, "Coupons"),
    SALE(4, "Sale"),
    RENTAL(4, "Rental"),
    GIFTS(4, "Gifts"),
}