package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.lens.BiDiLens
import org.http4k.routing.path
import ru.yarsu.operations.AddNewContrib
import ru.yarsu.classes.Contribution
import ru.yarsu.formErrors.FormErrorsContrib
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.classes.User
import ru.yarsu.functions.generateID
import ru.yarsu.web.models.ErrorNewContribVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDate
import java.util.UUID

class NewContribHandler(val htmlView: ContextAwareViewRender,
                        val addNewContrib: AddNewContrib,
                        val projectsStorage: ProjectsStorage,
                        val userLens: BiDiLens<Request, User?>): HttpHandler {
    override fun invoke(request: Request): Response {
        val user = userLens(request)
        val name: String? = request.path("name")
        if (name.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val project: Projects = projectsStorage.getByName(name)
            ?: return Response(Status.NOT_FOUND)
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
        val currentDate = LocalDate.now()
        val id = generateID()
        val formattedId = UUID.fromString(id)
        if (user != null) {
            addNewContrib.get(project, Contribution(user.idUser, currentDate, trueSum, formattedId))
        }
        return Response(Status.FOUND).header("Location", "/$name/$id")
    }
}