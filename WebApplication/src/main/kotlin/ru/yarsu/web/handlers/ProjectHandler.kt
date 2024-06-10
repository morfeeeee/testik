package ru.yarsu.web.handlers

import org.http4k.core.*
import org.http4k.routing.path
import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Paginator
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.models.ProjectVM
import ru.yarsu.web.templates.ContextAwareViewRender

class ProjectHandler(val htmlView: ContextAwareViewRender, val projectsStorage: ProjectsStorage): HttpHandler {
    override fun invoke(request: Request): Response {
        val name: String? = request.path("name")
        val parametrs = request.uri.queries()
        val min = parametrs.findSingle("min")
        val max = parametrs.findSingle("max")
        val num = parametrs.findSingle("num")
        if (name.isNullOrEmpty() || num.orEmpty() == "" || num == null) {
            return Response(Status.NOT_FOUND)
        }
        val trueName: String = name
        val trueNum = num.toInt()
        val minInt: Double? = min?.takeIf { it.isNotEmpty() }?.toDoubleOrNull()
        val maxInt: Double? = max?.takeIf { it.isNotEmpty() }?.toDoubleOrNull()
        val project: Projects = projectsStorage.getByName(name)
            ?: return Response(Status.NOT_FOUND)
        val filteredContribution: List<Contribution> = projectsStorage.filterMaxAndMin(minInt, maxInt, project)
        val countPages = projectsStorage.numberOfPages(filteredContribution)
        val paginator = Paginator(request.uri, trueNum, countPages)
        val listProjects = projectsStorage.getAllProjects()
        val contr = projectsStorage.getVznosByPage(filteredContribution, trueNum)
        val model = ProjectVM(listProjects, project, trueName, trueNum, minInt, maxInt, contr, paginator)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}