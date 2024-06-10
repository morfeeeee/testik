package ru.yarsu.web.filters

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.lens.BiDiLens
import ru.yarsu.classes.Permissions
import ru.yarsu.storages.PermissionsStorage
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.classes.User
import ru.yarsu.web.models.ErrorAccessVM
import ru.yarsu.web.templates.ContextAwareViewRender

class PermissionsHandler(val next: HttpHandler,
                         val htmlView: ContextAwareViewRender,
                         val projectsStorage: ProjectsStorage,
                         val canUse: (Permissions) -> Boolean,
                         val userLens: BiDiLens<Request, User?>
): HttpHandler{
    override fun invoke(request: Request): Response {
        val listProjects = projectsStorage.getAllProjects()
        val response = next(request)
        val user = userLens(request)
        if (user != null) {
            val role = PermissionsStorage().getByKey(user.role)!!
            if (canUse(role)) {
                return response
            }
            return response.with(htmlView(request) of ErrorAccessVM(listProjects))
        }
        return response.with(htmlView(request) of ErrorAccessVM(listProjects))
    }
}