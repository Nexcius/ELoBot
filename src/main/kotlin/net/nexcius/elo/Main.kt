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


    // TODO: Add config


    val session = SlackSessionFactory.createWebSocketSlackSession(Configuration.SLACK_API_KEY)

    session.connect()


    val x = Jedis(Configuration.REDIS_HOST)



    println("Connected")
    session.joinChannel(Configuration.SLACK_CHANNEL)
    println("JOINED")

    session.addMessagePostedListener({ slackMessagePosted, slackSession ->
        slackMessagePosted.channel.let { channel ->
            if(channel.name != Configuration.SLACK_CHANNEL
                    || slackMessagePosted.sender.isBot
                    || !slackMessagePosted.messageContent.startsWith("elo", true)) {
                return@addMessagePostedListener
            }

            slackSession.sendMessage(channel, "HEY FUCKERS: " + slackMessagePosted.messageContent)
        }

    })


    val channel = session.findChannelByName(Configuration.SLACK_CHANNEL)
    channel.let { ch ->
        session.sendMessage(ch, "Hey!!!")
        session.sendMessage(ch, "MOAHAHA")

    }


    readLine()

    session.disconnect()
}
