package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects

class ProjectEditVM(val listProjects: List<Projects>,
                    val projects: Projects) : ViewModel