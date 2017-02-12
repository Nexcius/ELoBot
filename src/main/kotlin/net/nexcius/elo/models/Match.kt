package net.nexcius.elo.models

import net.nexcius.elo.util.EloRating
import java.util.*

/*
 * Copyright (c) 2015 Schibsted Media Group. All rights reserved
 */
data class Match(val teamA: List<Player>, val teamB: List<Player>,
                 val resultA: Int, val resultB: Int,
                 val matchId: String = UUID.randomUUID().toString(),
                 val date: Date = Date()) {

    val isDraw = resultA == resultB
    val winner = if (resultA > resultB) teamA else teamB
    val looser = if (resultA < resultB) teamA else teamB

    val scorePercentageA = resultA.toFloat() / (resultA + resultB)
    val scorePercentageB = resultB.toFloat() / (resultA + resultB)

    private val scoreChanges = EloRating.getChange(this)
    val scoreChangeA: Int
        get() = scoreChanges.first
    val scoreChangeB: Int
        get() = scoreChanges.first


    fun getUpdatedScores(): List<Player> {
        return teamA.map { it.copy(elo = it.elo + scoreChangeA) } + teamB.map { it.copy(elo = it.elo + scoreChangeB) }
    }
}