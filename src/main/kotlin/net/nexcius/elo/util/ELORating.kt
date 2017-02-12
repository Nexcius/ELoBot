package net.nexcius.elo.util

import net.nexcius.elo.models.Match

private fun Double.round(): Int = Math.round(this).toInt()


object EloRating {
    /**
     * Returns an adaptive kFactor based on elo tiers
     */
    val kFactor: (Int) -> Int = { elo: Int ->
        require(elo >= 0){"EloRating $elo must be a positive number"}

        when {
            elo < 2100 -> 32
            elo < 2400 -> 24
            else -> 16
        }
    }

    /**
     * Returns the change in scores for team A and team B
     */
    fun getChange(match: Match): Pair<Int, Int> {
        val averageEloA = match.teamA.map{ it.elo }.average()
        val averageEloB = match.teamB.map{ it.elo }.average()

        val transformedRating1 = Math.pow(10.0, averageEloA / 400.0)
        val transformedRating2 = Math.pow(10.0, averageEloB / 400.0)

        val estimate1 = transformedRating1 / (transformedRating1 + transformedRating2)
        val estimate2 = transformedRating2 / (transformedRating1 + transformedRating2)

        val ratingChange1 = kFactor(averageEloA.toInt()) * (match.scorePercentageA - estimate1)
        val ratingChange2 = kFactor(averageEloB.toInt()) * (match.scorePercentageB - estimate2)

        return Pair(ratingChange1.round(), ratingChange2.round())
    }
}