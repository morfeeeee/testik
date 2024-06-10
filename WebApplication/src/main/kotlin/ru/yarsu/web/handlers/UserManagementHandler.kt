package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.models.UserManagementVM
import ru.yarsu.web.templates.ContextAwareViewRender

class UserManagementHandler(val htmlView: ContextAwareViewRender,
                            val projectsStorage: ProjectsStorage,
                            val userStorage: UserStorage
): HttpHandler {
    override fun invoke(request: Request): Response {
        val listUsers = userStorage.getAllUsers()
        val listProjects = projectsStorage.getAllProjects()
        val model = UserManagementVM(listProjects, listUsers)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}