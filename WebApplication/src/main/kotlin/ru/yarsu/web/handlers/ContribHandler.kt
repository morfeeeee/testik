package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.ContribVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.util.UUID

class ContribHandler(val htmlView: ContextAwareViewRender, val projectsStorage: ProjectsStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val name: String? = request.path("name")
        if (name.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val project: Projects = projectsStorage.getByName(name)
            ?: return Response(Status.NOT_FOUND)
        val id: String = request.path("id") ?: return Response(Status.NOT_FOUND)
        val formattedId = UUID.fromString(id)
        val contrib = projectsStorage.getVznosById(formattedId) ?: return Response(Status.NOT_FOUND)
        val listProjects = projectsStorage.getAllProjects()
        val model = ContribVM(listProjects, contrib, name)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}