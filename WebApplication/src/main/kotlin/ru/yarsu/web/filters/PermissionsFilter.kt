package ru.yarsu.web.filters

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.lens.BiDiLens
import ru.yarsu.classes.Permissions
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.classes.User
import ru.yarsu.web.templates.ContextAwareViewRender

class PermissionsFilter(
    val htmlView: ContextAwareViewRender,
    val userLens: BiDiLens<Request, User?>,
    val canUse: (Permissions) -> Boolean,
    val projectsStorage: ProjectsStorage
): Filter {
    override fun invoke(next: HttpHandler): HttpHandler{
        return PermissionsHandler(next, htmlView, projectsStorage, canUse, userLens)
    }
}