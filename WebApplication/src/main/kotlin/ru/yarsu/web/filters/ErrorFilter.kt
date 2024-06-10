package ru.yarsu.web.filters

import org.http4k.core.HttpHandler
import org.http4k.core.Filter
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.templates.ContextAwareViewRender


class ErrorFilter(val htmlView: ContextAwareViewRender,
                  val projectsStorage: ProjectsStorage
): Filter{
    override fun invoke(next: HttpHandler): HttpHandler{
        return ErrorHandler(next, htmlView, projectsStorage)
    }
}