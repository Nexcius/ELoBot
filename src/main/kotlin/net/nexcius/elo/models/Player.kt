package net.nexcius.elo.models

import net.nexcius.elo.Configuration

/*
 * Copyright (c) 2015 Schibsted Media Group. All rights reserved
 */
data class Player(val name: String, val elo: Int = Configuration.DEFAULT_RATING, val wins: Int, val losses: Int) {
    val gamesPlayed = wins + losses
}