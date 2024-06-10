package ru.yarsu.storages

import ru.yarsu.classes.Company
import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Projects
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class ProjectsStorage(projects: List<Projects>) {
    private val projectsMap = mutableMapOf<UUID, Projects>()
    init {
        projects.forEach { addProject(it) }
    }

    fun getAllProjects(): List<Projects> {
        return projectsMap.values.toList()
    }
    fun addProject(pr: Projects) {
        projectsMap[pr.id] = pr
    }
    fun addContrib(pr: Projects, contrib: Contribution){
        pr.contrib.add(contrib)
    }
    fun getByName(name: String): Projects? {
        for (i in getAllProjects()) {
            if (name == i.getNameProject(i)) {
                return i
            }
        }
        return null
    }
    fun numberOfPages(listVznos: List<Contribution>): Int {
        if (listVznos.size % 10 == 0) {
            return listVznos.size / 10
        }
        return (listVznos.size / 10) + 1
    }
    fun getVznosByPage(listVznos: List<Contribution>, pageNumber: Int): List<Contribution> {
        val pageSize = 10
        val startIndex = (pageNumber - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, listVznos.size)

        return if (startIndex < endIndex) {
            listVznos.subList(startIndex, endIndex)
        } else {
            emptyList()
        }
    }
    fun filterMaxAndMin(min: Double?, max: Double?, project: Projects?): List<Contribution> {
        return when {
            project == null -> emptyList()
            min == null && max == null -> project.contrib
            min != null && max == null -> project.contrib.filter { it.sum >= min }
            min != null && max != null -> project.contrib.filter { it.sum in min..max }
            min == null && max != null -> project.contrib.filter { it.sum <= max }
            else -> emptyList()
        }
    }
    fun getVznosById(id: UUID): Contribution?{
        for(i in getAllProjects()){
            for(j in i.contrib){
                if(j.id == id){
                    return j
                }
            }
        }
        return null
    }
    fun getPrByVznos(contrib: Contribution): Projects? {
        for (i in getAllProjects()) {
            for (j in i.contrib) {
                if (j == contrib) {
                    return i
                }
            }
        }
        return null
    }
    fun contribEdit(contrib: Contribution, sum: Double) {
        val pr = getPrByVznos(contrib)
        val currentDate = LocalDate.now()
        val newContrib = Contribution(contrib.idUSER, currentDate, sum, contrib.id)
        if (pr != null) {
            if(!pr.contrib.remove(contrib)){
                return
            }
        }
        if (pr != null) {
            addContrib(pr, newContrib)
        }
    }
    fun contribDelete(contrib: Contribution) {
        val pr = getPrByVznos(contrib)
        if (pr != null) {
            if(!pr.contrib.remove(contrib)){
                return
            }
        }
    }
    fun projectEdit(project: Projects, name: String, company: Company, targetSum: Long, dateEnd: LocalDateTime, description: String) {
        val newProject = Projects(project.idMan, project.id, name, project.contrib, company, targetSum, project.dateStart, dateEnd, description)
        projectsMap[project.id] = newProject
    }
    fun projectDelete(project: Projects) {
        if(!projectsMap.remove(project.id, project)){
            return
        }
    }
}