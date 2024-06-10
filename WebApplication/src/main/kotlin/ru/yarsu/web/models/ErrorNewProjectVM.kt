package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects
import ru.yarsu.classes.ValueErrors

class ErrorNewProjectVM(val listProjects: List<Projects>,
                        val name: String?,
                        val nameCompany: String?,
                        val namePerson: String?,
                        val surnamePerson: String?,
                        val email: String?,
                        val targetSum: String?,
                        val dateEnd: String?,
                        val description: String?,
                        val errors: ValueErrors) : ViewModel