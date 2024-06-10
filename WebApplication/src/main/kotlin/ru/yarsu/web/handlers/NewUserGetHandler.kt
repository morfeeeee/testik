package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.NewUserVM
import ru.yarsu.web.templates.ContextAwareViewRender

class NewUserGetHandler(val htmlView: ContextAwareViewRender,
                        val projectsStorage: ProjectsStorage
): HttpHandler {
    override fun invoke(request: Request): Response {
        val listProjects = projectsStorage.getAllProjects()
        val model = NewUserVM(listProjects)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}