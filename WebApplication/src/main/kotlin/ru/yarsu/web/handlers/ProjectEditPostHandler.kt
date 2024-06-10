package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.routing.path
import ru.yarsu.classes.Company
import ru.yarsu.formErrors.FormErrorsProject
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.ErrorNewProjectVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ProjectEditPostHandler(val htmlView: ContextAwareViewRender,
                             val projectsStorage: ProjectsStorage
): HttpHandler {
    override fun invoke(request: Request): Response {
        val namePr: String? = request.path("name")
        if (namePr.isNullOrEmpty()) {
            return Response(Status.NOT_FOUND)
        }
        val project: Projects = projectsStorage.getByName(namePr)
            ?: return Response(Status.NOT_FOUND)
        val form = request.form()
        val name = form.findSingle("name")
        val name1 = name.orEmpty()
        val nameS = name1.replace("\\s".toRegex(), "")
        val nameCompany = form.findSingle("nameCompany")
        val namePerson = form.findSingle("namePerson")
        val surnamePerson = form.findSingle("surnamePerson")
        val email = form.findSingle("email")
        val targetSum = form.findSingle("targetSum")
        val dateStart = LocalDateTime.now().withSecond(0).withNano(0)
        val dateEnd = form.findSingle("dateEnd")
        val description = form.findSingle("description")
        val formErrors = FormErrorsProject(name, nameCompany, namePerson, surnamePerson, email, targetSum, dateStart, dateEnd, description)
        val errors = formErrors.getFormErrors()
        if (projectsStorage.getByName(nameS) != null && projectsStorage.getByName(nameS)!!.id != project.id){
            errors.addError(name, "Проект с таким названием уже существует")
        }
        val listProjects = projectsStorage.getAllProjects()
        if(errors.isError()){
            val model = ErrorNewProjectVM(listProjects, name, nameCompany, namePerson, surnamePerson, email, targetSum, dateEnd, description, errors)
            return Response(Status.OK).with(htmlView(request) of model)
        }
        val nameCompany1 = nameCompany.orEmpty()
        val namePerson1 = namePerson.orEmpty()
        val surnamePerson1 = surnamePerson.orEmpty()
        val email1 = email.orEmpty()
        val targetSum1 = targetSum.orEmpty().toLong()
        val dateEnd1 = dateEnd.orEmpty()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateEND = LocalDateTime.parse(dateEnd1, formatter)
        val description1 = description.orEmpty()
        projectsStorage.projectEdit(project, name1, Company(nameCompany1, namePerson1, surnamePerson1, email1), targetSum1, dateEND, description1)
        return Response(Status.FOUND).header("Location", "/$nameS?num=1")
    }
}