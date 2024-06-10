package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.routing.path
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.storages.UserStorage
import java.util.UUID

class RoleUserPostHandler(val projectsStorage: ProjectsStorage, val userStorage: UserStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val idUser: String? = request.path("idUser")
        if (idUser.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val form = request.form()
        val role = form.findSingle("role").toString()
        val user = userStorage.getUserById(UUID.fromString(idUser)) ?: return Response(Status.NOT_FOUND)
        userStorage.editRoleUser(user, role)
        return Response(Status.FOUND).header("Location", "/users")
    }
}