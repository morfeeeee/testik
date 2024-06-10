package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Projects

class ContribVM(val listProjects: List<Projects>,
                val contrib: Contribution,
                val name: String) : ViewModel