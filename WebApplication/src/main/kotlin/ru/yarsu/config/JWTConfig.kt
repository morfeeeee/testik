package ru.yarsu.config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.cloudnative.env.Secret
import org.http4k.lens.long
import org.http4k.lens.secret

data class JWTConfig(
    val secret: Secret,
    val organization: String,
    val tokenValidity: Long
) {
    companion object {
        val secretLens = EnvironmentKey.secret().required("jwt.secret", "JWT secret")
        val organizationLens = EnvironmentKey.required("jwt.organization", "JWT organization")
        val tokenValidityLens = EnvironmentKey.long().required("jwt.tokenValidity", "JWT token validity in ms")
        fun readJWTConfig(env: Environment): JWTConfig {
            return JWTConfig(
                secret = JWTConfig.secretLens(env),
                organization = JWTConfig.organizationLens(env),
                tokenValidity = JWTConfig.tokenValidityLens(env)
            )
        }
    }
}