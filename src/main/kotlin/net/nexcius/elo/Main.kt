package net.nexcius.elo

fun main(args: Array<String>) {
    println("Hello, world!")

    val f = EloRating()
    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.TEAM_ONE_WIN)
    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.TEAM_ONE_WIN)
    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.TEAM_ONE_WIN)
    f.print()

    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.DRAW)

    f.print()

}