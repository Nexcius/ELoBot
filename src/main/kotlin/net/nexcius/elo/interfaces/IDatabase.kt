package net.nexcius.elo.interfaces

import net.nexcius.elo.models.Match

/*
 * Copyright (c) 2015 Schibsted Media Group. All rights reserved
 */
interface IDatabase {
    fun getMatch(matchId: String): Match?
    fun deleteMatch(matchId: String): Boolean
    fun updateMatch(matchId: String, substitute: Match): Boolean

    fun getMatchesForPlayer(player: String): List<Match>
    fun getAllMatches(): List<Match>
    fun getMatches(filter: (Match) -> Boolean): List<Match>
}