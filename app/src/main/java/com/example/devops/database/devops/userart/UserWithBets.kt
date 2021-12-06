package com.example.devops.database.devops.userart

import androidx.room.Embedded
import androidx.room.Relation
import com.example.devops.database.devops.bet.Bet

data class UserWithBets(
    @Embedded val userArt: UserArt,
    @Relation(
        parentColumn = "userArtId",
        entityColumn = "userBidderId"
    )
    val bets: List<Bet>
)