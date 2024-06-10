package ru.yarsu.operations

import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage

interface AddNewContribOperation {
    fun get(projects: Projects, contribution: Contribution)
}
class AddNewContrib(
    private val storage: ProjectsStorage
) : AddNewContribOperation {
    override fun get(projects: Projects, contribution: Contribution) = storage.addContrib(projects, contribution)
}