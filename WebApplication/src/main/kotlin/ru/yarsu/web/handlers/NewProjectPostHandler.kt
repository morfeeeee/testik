package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import org.http4k.lens.BiDiLens
import ru.yarsu.operations.AddNewProject
import ru.yarsu.classes.Company
import ru.yarsu.formErrors.FormErrorsProject
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.classes.User
import ru.yarsu.functions.generateID
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.models.ErrorNewProjectVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class NewProjectPostHandler(val htmlView: ContextAwareViewRender,
                            val addNewProject: AddNewProject,
                            val projectsStorage: ProjectsStorage,
                            val userLens: BiDiLens<Request, User?>,
                            val userStorage: UserStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val user = userLens(request)
        val form = request.form()
        val name = form.findSingle("name")
        val nameCompany = form.findSingle("nameCompany")
        val namePerson = form.findSingle("namePerson")
        val surnamePerson = form.findSingle("surnamePerson")
        val email = form.findSingle("email")
        val targetSum = form.findSingle("targetSum")
        val dateStart = LocalDateTime.now().withSecond(0).withNano(0)
        val dateEnd = form.findSingle("dateEnd")
        val description = form.findSingle("description")
        val formErrors = FormErrorsProject(name, nameCompany, namePerson, surnamePerson, email, targetSum, dateStart, dateEnd, description)
        val name1 = name.orEmpty().trim()
        val nameS = name1.replace("\\s".toRegex(), "")
        val errors = formErrors.getFormErrors()
        if (projectsStorage.getByName(nameS) != null){
            errors.addError(name, "Проект с таким названием уже существует")
        }
        val listProjects = projectsStorage.getAllProjects()
        if(errors.isError()){
            val model = ErrorNewProjectVM(listProjects, name, nameCompany, namePerson, surnamePerson, email, targetSum, dateEnd, description, errors)
            return Response(Status.OK).with(htmlView(request) of model)
        }
        val nameCompany1 = nameCompany.orEmpty().trim()
        val namePerson1 = namePerson.orEmpty().trim()
        val surnamePerson1 = surnamePerson.orEmpty().trim()
        val email1 = email.orEmpty().trim()
        val targetSum1 = targetSum.orEmpty().trim().toLong()
        val dateEnd1 = dateEnd.orEmpty().trim()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val dateEND = LocalDateTime.parse(dateEnd1, formatter)
        val description1 = description.orEmpty().trim()
        val id = generateID()
        val formattedId = UUID.fromString(id)
        if (user != null) {
            addNewProject.get(Projects(user.idUser, formattedId, name1, mutableListOf(), Company(nameCompany1, namePerson1, surnamePerson1, email1), targetSum1, dateStart, dateEND, description1 ))
            user.role = "holderOfCompany"
            userStorage.editRoleUser(user, "holderOfCompany")
        }
        return Response(Status.FOUND).header("Location", "/$nameS?num=1")
    }
}