package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.formErrors.FormErrorsContrib
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.ErrorNewContribVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.util.UUID

class ContribEditHandler(val htmlView: ContextAwareViewRender, val projectsStorage: ProjectsStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val name: String? = request.path("name")
        if (name.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val project: Projects = projectsStorage.getByName(name)
            ?: return Response(Status.NOT_FOUND)
        val id: String = request.path("id") ?: return Response(Status.NOT_FOUND)
        val formattedId = UUID.fromString(id)
        val form = request.form()
        val sum = form.findSingle("sum")
        val formErrorsContrib = FormErrorsContrib(sum)
        val errors = formErrorsContrib.getFormErrors()
        val listProjects = projectsStorage.getAllProjects()
        if(errors.isError()){
            val model = ErrorNewContribVM(listProjects, errors, sum)
            return Response(Status.OK).with(htmlView(request) of model)
        }
        val trueSum = sum.orEmpty().toDouble()
        val contrib = projectsStorage.getVznosById(formattedId)?: return Response(Status.NOT_FOUND)
        projectsStorage.contribEdit(contrib, trueSum)
        return Response(Status.FOUND).header("Location", "/$name/$id")
    }
}