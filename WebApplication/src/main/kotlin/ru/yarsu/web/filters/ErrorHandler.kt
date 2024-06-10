package ru.yarsu.web.filters

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.ErrorVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ErrorHandler(val next: HttpHandler,
                   val htmlView: ContextAwareViewRender,
                   val projectsStorage: ProjectsStorage
): HttpHandler {
    override fun invoke(request: Request): Response {
        val response = next(request)
        val listProjects = projectsStorage.getAllProjects()
        if(response.status != Status.OK){
            val model = ErrorVM(listProjects)
            return response.with(htmlView(request) of model)
        }
        return response
    }
}