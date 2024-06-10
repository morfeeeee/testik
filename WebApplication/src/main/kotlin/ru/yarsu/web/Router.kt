package ru.yarsu.web

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.then
import org.http4k.lens.BiDiLens
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import ru.yarsu.operations.AddNewContrib
import ru.yarsu.operations.AddNewProject
import ru.yarsu.classes.JwtTools
import ru.yarsu.classes.Permissions
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.storages.SaltStorage
import ru.yarsu.classes.User
import ru.yarsu.storages.UserStorage
import ru.yarsu.web.filters.PermissionsFilter
import ru.yarsu.web.handlers.ContribDeleteHandler
import ru.yarsu.web.handlers.ContribEditGetHandler
import ru.yarsu.web.handlers.ContribEditHandler
import ru.yarsu.web.handlers.ContribHandler
import ru.yarsu.web.handlers.ContribPostDeleteHandler
import ru.yarsu.web.handlers.HomeHandler
import ru.yarsu.web.handlers.ListContribHandler
import ru.yarsu.web.handlers.LoginGetHandler
import ru.yarsu.web.handlers.LoginPostHandler
import ru.yarsu.web.handlers.LogoutHandler
import ru.yarsu.web.handlers.NewContribHandler
import ru.yarsu.web.handlers.NewGetContribHandler
import ru.yarsu.web.handlers.NewProjectHandler
import ru.yarsu.web.handlers.NewProjectPostHandler
import ru.yarsu.web.handlers.NewUserGetHandler
import ru.yarsu.web.handlers.NewUserPostHandler
import ru.yarsu.web.handlers.ProjectDeleteHandler
import ru.yarsu.web.handlers.ProjectDeletePostHandler
import ru.yarsu.web.handlers.ProjectEditHandler
import ru.yarsu.web.handlers.ProjectEditPostHandler
import ru.yarsu.web.handlers.ProjectHandler
import ru.yarsu.web.handlers.RoleUserGetHandler
import ru.yarsu.web.handlers.RoleUserPostHandler
import ru.yarsu.web.handlers.UserDeleteGetHandler
import ru.yarsu.web.handlers.UserDeletePostHandler
import ru.yarsu.web.handlers.UserManagementHandler
import ru.yarsu.web.templates.ContextAwareViewRender

fun router2(projectsStorage: ProjectsStorage,
            saltStorage: SaltStorage,
            userStorage: UserStorage,
            userLens: BiDiLens<Request, User?>,
            jwtTools: JwtTools,
            htmlView: ContextAwareViewRender): RoutingHttpHandler{
    val addNewContribOperation = AddNewContrib(projectsStorage)
    val addNewProjectOperation = AddNewProject(projectsStorage)
    return routes(
        "/" bind Method.GET to HomeHandler(htmlView, projectsStorage),
        "/new" bind Method.POST to NewProjectPostHandler(htmlView, addNewProjectOperation, projectsStorage, userLens, userStorage),
        "/new" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canAddProject, projectsStorage)
            .then(NewProjectHandler(htmlView, projectsStorage)),
        "/login" bind Method.GET to LoginGetHandler(htmlView, projectsStorage),
        "/login" bind Method.POST to LoginPostHandler(htmlView, projectsStorage, saltStorage, userStorage, jwtTools),
        "/logout" bind Method.GET to LogoutHandler(),
        "/newUser" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canAddUser, projectsStorage)
            .then(NewUserGetHandler(htmlView, projectsStorage)),
        "/newUser" bind Method.POST to NewUserPostHandler(htmlView, projectsStorage, saltStorage, userStorage),
        "/listContrib" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canListContrib, projectsStorage)
            .then(ListContribHandler(htmlView, projectsStorage, userLens)),
        "/users" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canListUsers, projectsStorage)
            .then(UserManagementHandler(htmlView, projectsStorage, userStorage)),
        "/users/{idUser}/delete" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canListUsers, projectsStorage)
            .then(UserDeleteGetHandler(htmlView, projectsStorage, userStorage)),
        "/users/{idUser}/delete" bind Method.POST to UserDeletePostHandler(projectsStorage, userStorage),
        "/users/{idUser}/role" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canListUsers, projectsStorage)
            .then(RoleUserGetHandler(htmlView, projectsStorage, userStorage)),
        "/users/{idUser}/role" bind Method.POST to RoleUserPostHandler(projectsStorage, userStorage),
        "/{name}" bind Method.GET to ProjectHandler(htmlView, projectsStorage),
        "/{name}/edit" bind Method.POST to ProjectEditPostHandler(htmlView, projectsStorage),
        "/{name}/edit" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canEditProject, projectsStorage)
            .then(ProjectEditHandler(htmlView, projectsStorage)),
        "/{name}/delete" bind Method.POST to ProjectDeletePostHandler(projectsStorage),
        "/{name}/delete" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canDeleteProject, projectsStorage)
            .then(ProjectDeleteHandler(htmlView, projectsStorage)),
        "/{name}/new" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canAddContrib, projectsStorage)
            .then(NewGetContribHandler(htmlView, projectsStorage)),
        "/{name}/new" bind Method.POST to NewContribHandler(htmlView, addNewContribOperation, projectsStorage, userLens),
        "/{name}/{id}" bind Method.GET to ContribHandler(htmlView, projectsStorage),
        "/{name}/{id}/edit" bind Method.POST to ContribEditHandler(htmlView, projectsStorage),
        "/{name}/{id}/edit" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canEditContrib, projectsStorage)
            .then(ContribEditGetHandler(htmlView, projectsStorage)),
        "/{name}/{id}/delete" bind Method.POST to ContribPostDeleteHandler(projectsStorage),
        "/{name}/{id}/delete" bind Method.GET to PermissionsFilter(htmlView, userLens, Permissions::canDeleteContrib, projectsStorage)
            .then(ContribDeleteHandler(htmlView, projectsStorage)),
    )
}
