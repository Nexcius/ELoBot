package net.nexcius.elo

import com.natpryce.konfig.*

/*
 * Copyright (c) 2015 Schibsted Media Group. All rights reserved
 */
object Configuration {
    private val config = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("defaults.properties")

    val SLACK_API_KEY = config[Key("slack.apikey", stringType)]
    val SLACK_CHANNEL = config[Key("slack.channel", stringType)]

    val REDIS_HOST = config[Key("redis.host", stringType)]

    val DEFAULT_RATING = 1400

}