package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects

class UserDeleteVM(val listProjects: List<Projects>,
                   val name: String) : ViewModel