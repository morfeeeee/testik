package ru.yarsu.classes

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

class JwtTools(val secret: String, val organization: String, val time: Long)
{
    fun createToken(idUser: String): String? {
        try { // Необходимо обеспечить хранение секретной строки в настройках
            val algorithm = HMAC512(secret)
            val token = JWT.create()
                .withIssuer(organization)
                .withSubject(idUser)
                .withExpiresAt(Date(Date().time + time))
                .sign(algorithm)
            return token
        } catch (exception: JWTCreationException){
            // Неправильная конфигурация или ошибка конвертации утверждений
        }
        return null
    }

    fun checkToken(token: String): String? {
        val algorithm = HMAC512(secret)
        val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer(organization)
            .build() // Можно создать единожды для приложения
        try {
            val decodedJWT: DecodedJWT = verifier.verify(token);
            return decodedJWT.subject
        } catch (exception: JWTVerificationException){
            // Неправильная подпись или утверждения
        }
        return null
    }
}