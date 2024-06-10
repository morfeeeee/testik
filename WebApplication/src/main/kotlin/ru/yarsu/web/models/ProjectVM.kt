package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Paginator
import ru.yarsu.classes.Projects

class ProjectVM(val listProjects: List<Projects>,
                val projects: Projects,
                val name: String,
                val num: Int?,
                val minn: Double?,
                val max: Double?,
                val contr: List<Contribution>,
                val paginator: Paginator) : ViewModel