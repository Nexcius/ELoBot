package net.nexcius.elo

import com.sun.xml.internal.ws.encoding.soap.DeserializationException
import kotlin.comparisons.compareByDescending

private fun Double.round(): Int = Math.round(this).toInt()


class EloRating(saveFile: String? = null) {
    init {
        if (saveFile != null) {
            load(saveFile)
        }
    }

    private val players = mutableMapOf<String, Int>()
    val ratings: List<Pair<Int, String>>
        get() = players.toList().sortedWith(compareByDescending { it.second }).map {Pair(it.second, it.first)}


    fun register(teamOne: List<String>, teamTwo: List<String>, teamOneScore: Int, teamTwoScore: Int): Pair<Int, Int> =
            register(teamOne, teamTwo, teamOneScore.toFloat(), teamTwoScore.toFloat())

    fun register(teamOne: List<String>, teamTwo: List<String>, result: Result): Pair<Int, Int> {
        val scores = when(result) {
            Result.TEAM_ONE_WIN -> Pair(1.0f, 0.0f)
            Result.TEAM_TWO_WIN -> Pair(0.0f, 1.0f)
            Result.DRAW -> Pair(0.5f, 0.5f)
        }
        return register(teamOne, teamTwo, scores.first, scores.second)
    }

    fun register(teamOne: List<String>, teamTwo: List<String>, teamOneScore: Float, teamTwoScore: Float): Pair<Int, Int> {
        val t1 = teamOne.map { it to players.getOrElse(it) { 1400 } }.toMap()
        val t2 = teamTwo.map { it to players.getOrElse(it) { 1400 } }.toMap()

        val t1Score = teamOneScore / (teamOneScore + teamTwoScore)
        val t2Score = teamTwoScore / (teamOneScore + teamTwoScore)
        val change = getChange(t1.values.average().toInt(), t2.values.average().toInt(), t1Score, t2Score)

        players.putAll( t1.mapValues { Math.max(0, it.value + change.first) })
        players.putAll( t2.mapValues { Math.max(0, it.value + change.second) })

        return change
    }


    fun print() {
        println("====================\n==== ELO Rating ====\n====================")
        println(ratings.map{"${it.first}: ${it.second}"}.joinToString("\n"))
    }

    fun save(): String {
        return players.map { "${it.key}:${it.value}" }.joinToString(",")
    }


    private fun getChange(rating1: Int, rating2: Int, teamOneScore: Float, teamTwoScore: Float): Pair<Int, Int> {
        val transformedRating1 = Math.pow(10.0, rating1.toDouble() / 400.0)
        val transformedRating2 = Math.pow(10.0, rating2.toDouble() / 400.0)

        val estimate1 = transformedRating1 / (transformedRating1 + transformedRating2)
        val estimate2 = transformedRating2 / (transformedRating1 + transformedRating2)

        val ratingChange1 = kFactor(rating1) * (teamOneScore - estimate1)
        val ratingChange2 = kFactor(rating2) * (teamTwoScore - estimate2)

        return Pair(ratingChange1.round(), ratingChange2.round())
    }

    private fun load(content: String) {
        content.split(",").map {
            val i = it.split(":")
            val player = i.first()
            val score = Integer.valueOf(i.last()) ?: throw DeserializationException("Unable to parse ratings")
            players.put(player, score)
        }
    }


    companion object {
        enum class Result {
            TEAM_ONE_WIN,
            TEAM_TWO_WIN,
            DRAW
        }

        val kFactor: (Int) -> Int = { elo: Int ->
            require(elo >= 0){"EloRating $elo must be a positive number"}

            when {
                elo < 2100 -> 32
                elo < 2400 -> 24
                else -> 16
            }
        }
    }
}