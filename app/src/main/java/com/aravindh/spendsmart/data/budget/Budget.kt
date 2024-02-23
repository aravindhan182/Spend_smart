package com.aravindh.spendsmart.data.budget

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "category")
    var category: Category,

    @ColumnInfo(name = "amount")
    var amount: Double
)

enum class Category {
    FOOD,
    TRANSPORT,
    PETROL,
    APPAREL,
    HEALTH,
    EDUCATION,
    GIFT,
    EMI,
    OTHER
}
