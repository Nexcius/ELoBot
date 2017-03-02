package net.nexcius.elo

object InputParser {
    fun tokenize(str: String): List<String> {
        return str.toUpperCase().split(" ").map{it.trim()}.filter(String::isNotBlank)
    }
}