package ru.yarsu.operations

import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage

interface AddNewProjectOperation {
    fun get(projects: Projects)
}
class AddNewProject(
    private val storage: ProjectsStorage
) : AddNewProjectOperation {
    override fun get(projects: Projects) = storage.addProject(projects)
}