package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.ProjectEditVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ProjectEditHandler(val htmlView: ContextAwareViewRender, val projectsStorage: ProjectsStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val name: String? = request.path("name")
        if (name.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val listProjects = projectsStorage.getAllProjects()
        val project: Projects = projectsStorage.getByName(name)
            ?: return Response(Status.NOT_FOUND)
        val model = ProjectEditVM(listProjects, project)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}