package ru.yarsu.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.BiDiLens
import ru.yarsu.classes.Contribution
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.classes.User
import ru.yarsu.web.models.ListContribVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ListContribHandler(val htmlView: ContextAwareViewRender, val projectsStorage: ProjectsStorage, val userLens: BiDiLens<Request, User?>): HttpHandler {
    override fun invoke(request: Request): Response {
        val user = userLens(request)
        val listProjects = projectsStorage.getAllProjects()
        val listContrib = mutableMapOf<String, List<Contribution>>()
        for (i in listProjects) {
            val list = mutableListOf<Contribution>()
            for (j in i.contrib) {
                if (j.idUSER == user?.idUser) {
                    list.add(j)
                }
            }
            if (list.isNotEmpty()) {
                listContrib[i.name] = list
            }
        }
        val model = ListContribVM(listProjects, listContrib)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}