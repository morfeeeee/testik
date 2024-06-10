package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Projects

class ListContribVM(val listProjects: List<Projects>,
                    val listContrib: Map<String, List<Contribution>>) : ViewModel