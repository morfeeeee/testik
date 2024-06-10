package ru.yarsu.config

import org.http4k.cloudnative.env.Environment
import ru.yarsu.config.JWTConfig.Companion.readJWTConfig
import ru.yarsu.config.WebConfig.Companion.defaultEnv
import ru.yarsu.config.WebConfig.Companion.from
data class AppConfig(val webConfig: WebConfig, val jwtConfig: JWTConfig)
val appEnv = Environment.fromResource("ru/yarsu/config/app.properties") overrides
        Environment.JVM_PROPERTIES overrides
        Environment.ENV overrides
        defaultEnv
fun readConfiguration(): AppConfig{
    return AppConfig(from(appEnv), readJWTConfig(appEnv))
}