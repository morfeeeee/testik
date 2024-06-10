package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects
import ru.yarsu.classes.ValueErrors

class ErrorNewContribVM(val listProjects: List<Projects>,
                        val errors: ValueErrors,
                        val sum: String?) : ViewModel