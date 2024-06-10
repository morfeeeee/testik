package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects
import ru.yarsu.classes.ValueErrors

class ErrorNewUserVM(val listProjects: List<Projects>,
                     val name: String?,
                     val password1: String?,
                     val password2: String?,
                     val errors: ValueErrors) : ViewModel