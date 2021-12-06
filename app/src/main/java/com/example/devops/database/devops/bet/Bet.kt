package com.example.devops.database.devops.bet

import androidx.room.*

@Entity(tableName = "bet_table")
data class Bet(

    @PrimaryKey(autoGenerate = true)
    var betId: Long = 0L,

    @ColumnInfo(name = "bet_amount")
    var betAmount: Double = 0.0,

    val userBidderId: Long,

    val productBetId: Long
)