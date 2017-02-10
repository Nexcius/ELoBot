package net.nexcius.elo

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory
import redis.clients.jedis.Jedis
import java.io.File

fun main(args: Array<String>) {
/*    val f = EloRating()
    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.TEAM_ONE_WIN)
    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.TEAM_ONE_WIN)
    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.TEAM_ONE_WIN)
    f.print()

    f.register(listOf("Player1"), listOf("Player2"), EloRating.Companion.Result.DRAW)

    f.print()
*/


    val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("defaults.properties")

    val v = config[Key("test.val", intType)]
    println(v)


    // TODO: Add config

    val API_KEY = "..."
    val session = SlackSessionFactory.createWebSocketSlackSession(API_KEY)

    session.connect()


    val x = Jedis("asd")


    println("Connected")
    session.joinChannel("spt-osl-fussball")
    println("JOINED")

    session.addMessagePostedListener({ slackMessagePosted, slackSession ->
        slackMessagePosted.channel.let { channel ->
            if(channel.name != "spt-osl-fussball"
                    || slackMessagePosted.sender.isBot
                    || !slackMessagePosted.messageContent.startsWith("elo", true)) {
                return@addMessagePostedListener
            }

            slackSession.sendMessage(channel, "HEY FUCKERS: " + slackMessagePosted.messageContent)
        }

    })


    val channel = session.findChannelByName("spt-osl-fussball")
    channel.let { ch ->
        session.sendMessage(ch, "Hey!!!")
        session.sendMessage(ch, "MOAHAHA")

    }


    readLine()

    session.disconnect()
}
