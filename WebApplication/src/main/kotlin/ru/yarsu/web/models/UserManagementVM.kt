package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects
import ru.yarsu.classes.User

class UserManagementVM(val listProjects: List<Projects>,
                       val listUsers: List<User>) : ViewModel