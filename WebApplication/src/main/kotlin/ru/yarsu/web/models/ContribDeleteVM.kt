package ru.yarsu.web.models

import org.http4k.template.ViewModel
import ru.yarsu.classes.Projects
import java.util.UUID

class ContribDeleteVM(val listProjects: List<Projects>,
                      val id: UUID) : ViewModel