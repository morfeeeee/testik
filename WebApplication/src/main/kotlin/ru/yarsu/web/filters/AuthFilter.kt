package ru.yarsu.web.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.cookie.cookie
import org.http4k.lens.RequestContextLens
import ru.yarsu.classes.JwtTools
import ru.yarsu.classes.User
import ru.yarsu.storages.UserStorage
import java.util.UUID

class AuthFilter(
    private val userStorage: UserStorage,
    private val userLens: RequestContextLens<User?>,
    private val jwtTools: JwtTools
) : Filter {
    override fun invoke(next: HttpHandler): HttpHandler = { request ->
        val authCookie = request.cookie("auth")
        if (authCookie != null) {
            val jwtToken = authCookie.value
            val idUser = jwtTools.checkToken(jwtToken)

            if (idUser != null) {
                val formattedId = UUID.fromString(idUser)
                val user = userStorage.getUserById(formattedId)
                if (user != null) {
                    val updatedRequest = userLens(user, request)
                    userLens[request] = user
                } else {
                    next(request)
                }
                next(request)
                }
             else {
                next(request)
            }
        } else {
            next(request)
        }
    }
}
