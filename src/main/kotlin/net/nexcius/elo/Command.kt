package net.nexcius.elo

enum class Command {
    RESULT,
    SCORES;

    companion object {
        fun fromString(input: String) = try { valueOf(input) } catch (e: IllegalArgumentException) { null }
    }
}
