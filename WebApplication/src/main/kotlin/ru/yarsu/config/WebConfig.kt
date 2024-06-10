package ru.yarsu.config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.int
import org.http4k.lens.nonEmptyString

data class WebConfig(
    val webPort: Int,
) {
    companion object {
        val webPortLens = EnvironmentKey.int().required("web.port", "Application web port")
        val saltLens = EnvironmentKey.nonEmptyString().required("auth.salt")
        val defaultEnv = Environment.defaults(
            webPortLens of 9000,
        )
        fun from(env: Environment): WebConfig {
            return WebConfig(webPortLens(env))
        }
    }
}