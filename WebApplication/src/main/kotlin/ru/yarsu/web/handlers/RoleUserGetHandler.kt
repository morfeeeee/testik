package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.models.RoleUserVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.util.UUID

class RoleUserGetHandler(val htmlView: ContextAwareViewRender, val projectsStorage: ProjectsStorage, val userStorage: UserStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val idUser: String? = request.path("idUser")
        if (idUser.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val listProjects = projectsStorage.getAllProjects()
        val user = userStorage.getUserById(UUID.fromString(idUser)) ?: return Response(Status.NOT_FOUND)
        val model = RoleUserVM(listProjects, user.nameUser)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}