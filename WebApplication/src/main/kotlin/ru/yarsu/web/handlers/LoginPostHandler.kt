package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.body.form
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.findSingle
import org.http4k.core.with
import ru.yarsu.formErrors.FormErrorsLogin
import ru.yarsu.classes.JwtTools
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.storages.SaltStorage
import ru.yarsu.storages.UserStorage
import ru.yarsu.functions.hashPassword
import ru.yarsu.web.models.ErrorLoginVM
import ru.yarsu.web.templates.ContextAwareViewRender

class LoginPostHandler(val htmlView: ContextAwareViewRender,
                       val projectsStorage: ProjectsStorage,
                       val saltStorage: SaltStorage,
                       val userStorage: UserStorage,
                       val jwtTools: JwtTools): HttpHandler {
    override fun invoke(request: Request): Response {
        val listProjects = projectsStorage.getAllProjects()
        val form = request.form()
        val login = form.findSingle("nameUser")
        val password1 = form.findSingle("password1")
        val passwordT = password1.orEmpty().trim()
        val formErrors = FormErrorsLogin(login, password1)
        val errors = formErrors.getFormErrors()
        val idUser = userStorage.getIdUserByLogin(login)
        if (idUser == null) {
            errors.addError(login, "Пользователя с таким логином не существует")
        }
        else {
            val password = passwordT + saltStorage.getSaltById(idUser)
            val hashPassword = hashPassword(password)
            if (!userStorage.truePassword(idUser, hashPassword)) {
                errors.addError(password1, "Неверный пароль")
            }

        }
        if(errors.isError()){
            val model = ErrorLoginVM(listProjects, login, password1, errors)
            return Response(Status.OK).with(htmlView(request) of model)
        }
        val token = jwtTools.createToken(idUser.toString()).toString()
        return Response(Status.FOUND)
            .header("Location", "/")
            .cookie(Cookie("auth", token))
    }
}