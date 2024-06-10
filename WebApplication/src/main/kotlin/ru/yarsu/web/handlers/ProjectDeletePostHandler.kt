package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.routing.path
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage

class ProjectDeletePostHandler(val projectsStorage: ProjectsStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val name: String? = request.path("name")
        if (name.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val project: Projects = projectsStorage.getByName(name)
            ?: return Response(Status.NOT_FOUND)
        val form = request.form()
        val check = form.findSingle("checkbox")

        if(check != null){
            projectsStorage.projectDelete(project)
            return Response(Status.FOUND).header("Location", "/")
        }

        return Response(Status.FOUND).header("Location", "/$name?num=1")
    }
}