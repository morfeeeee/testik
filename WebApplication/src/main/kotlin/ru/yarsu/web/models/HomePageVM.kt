package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects

class HomePageVM(val listProjects: List<Projects>) : ViewModel

