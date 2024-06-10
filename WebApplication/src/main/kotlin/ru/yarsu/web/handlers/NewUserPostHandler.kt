package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.findSingle
import org.http4k.core.with
import ru.yarsu.formErrors.FormErrorsUser
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.storages.SaltStorage
import ru.yarsu.classes.User
import ru.yarsu.storages.UserStorage
import ru.yarsu.functions.generateID
import ru.yarsu.functions.hashPassword
import ru.yarsu.web.models.ErrorNewUserVM
import ru.yarsu.web.templates.ContextAwareViewRender
import java.time.LocalDateTime
import java.util.UUID

class NewUserPostHandler(val htmlView: ContextAwareViewRender,
                         val projectsStorage: ProjectsStorage,
                         val saltStorage: SaltStorage,
                         val userStorage: UserStorage
): HttpHandler {
    override fun invoke(request: Request): Response {
        val listProjects = projectsStorage.getAllProjects()
        val form = request.form()
        val nameUser = form.findSingle("nameUser")
        val nameUserT = nameUser.orEmpty().trim()
        val password1 = form.findSingle("password1")
        val password2 = form.findSingle("password2")
        val role = form.findSingle("role").toString()
        val formErrors = FormErrorsUser(nameUser, password1, password2)
        val errors = formErrors.getFormErrors()
        val listUsers = userStorage.getAllUsers()
        for (i in listUsers) {
            if (nameUserT == i.nameUser) {
                errors.addError(nameUser, "Пользователь с таким ником уже существует")
            }
        }
        if(errors.isError()){
            return Response(Status.OK).with(htmlView(request) of (ErrorNewUserVM(listProjects, nameUser, password1, password2, errors)))
        }
        val passwordT = password1.orEmpty().trim()
        val id = generateID()
        val formattedId = UUID.fromString(id)
        saltStorage.addSalt(formattedId)
        val password = passwordT + saltStorage.getSaltById(formattedId)
        val date = LocalDateTime.now().withSecond(0).withNano(0)
        val hashPassword = hashPassword(password)
        userStorage.addUser(User(nameUserT, hashPassword, date, formattedId, role))
        return Response(Status.FOUND).header("Location", "/")
    }
}