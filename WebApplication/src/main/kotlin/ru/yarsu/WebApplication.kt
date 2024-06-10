package ru.yarsu

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.http4k.core.ContentType
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.routing.ResourceLoader
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import ru.yarsu.web.filters.ErrorFilter
import ru.yarsu.classes.Projects
import ru.yarsu.storages.ProjectsStorage
import ru.yarsu.web.router2
import java.io.File
import org.http4k.core.Filter
import ru.yarsu.config.readConfiguration
import ru.yarsu.classes.JwtTools
import ru.yarsu.storages.SaltStorage
import ru.yarsu.classes.User
import ru.yarsu.storages.UserStorage
import ru.yarsu.functions.fileJsonProjects
import ru.yarsu.functions.fileJsonSalt
import ru.yarsu.functions.fileJsonUsers
import java.util.UUID
import kotlin.concurrent.thread
import org.http4k.core.RequestContexts
import org.http4k.core.then
import org.http4k.filter.ServerFilters
import org.http4k.lens.RequestContextKey
import ru.yarsu.web.filters.AuthFilter
import ru.yarsu.web.templates.ContextAwarePebbleTemplates
import ru.yarsu.web.templates.ContextAwareViewRender

val objectMapper: ObjectMapper = ObjectMapper().findAndRegisterModules()
fun main() {
    val countProjects = 10
    if(!File("projects.json").exists()){
        val pr = fileJsonProjects(countProjects)
        if(!File("salt.json").exists()){
            val sl = fileJsonSalt(pr)
            if(!File("users.json").exists()){
                fileJsonUsers(pr, sl)
            }
        }
    }

    val listUsers: List<User> = objectMapper.readValue(File("users.json").readText())
    val listSalt: Map<UUID, String> = objectMapper.readValue(File("salt.json").readText())
    val listProjects: List<Projects> = objectMapper.readValue(File("projects.json").readText())
    val projectsStorage = ProjectsStorage(listProjects)
    val saltStorage = SaltStorage(listSalt)
    val userStorage = UserStorage(listUsers)

    val config = readConfiguration()
    val web = config.webConfig
    val jwt = config.jwtConfig
    val jwtTools = JwtTools(jwt.secret.toString(), jwt.organization, jwt.tokenValidity)

    val contexts = RequestContexts()
    val renderer = ContextAwarePebbleTemplates().HotReload("src/main/resources")
    val htmlView = ContextAwareViewRender.withContentType(renderer, ContentType.TEXT_HTML)
    val userLens = RequestContextKey.optional<User>(contexts, "user")
    val htmlViewWithContext = htmlView.associateContextLens("user", userLens)

    val errorFilter = ErrorFilter(htmlView, projectsStorage)
    val authFilter = AuthFilter(userStorage, userLens, jwtTools)
    val appWithStaticResources = ServerFilters.InitialiseRequestContext(contexts)
        .then(authFilter)
        .then(routes(
        static(ResourceLoader.Classpath("/ru/yarsu/public")),
        router2(projectsStorage, saltStorage, userStorage, userLens, jwtTools, htmlViewWithContext),
    ).withFilter(errorFilter))

    val server = appWithStaticResources.asServer(Netty(web.webPort)).start()
    val logger = LoggerFactory.getLogger("ru.yarsu.WebApplication")
    val loggingFilter = Filter { next: HttpHandler ->
        { request: Request ->
            val result = next(request)
            logger.atInfo().setMessage("Request").addKeyValue("URI", request.uri).log()
            result
        }
    }

    println("Server started on http://localhost:" + server.port())
    println("Press enter to exit application.")

    val hook = thread(start = false) {
        val fileSpecialistsWrite = File("projects.json")
        val stringSpecialists = objectMapper.writeValueAsString(projectsStorage.getAllProjects())
        fileSpecialistsWrite.writeText(stringSpecialists, Charsets.UTF_8)

        val fileUsers = File("users.json")
        val stringUsers = objectMapper.writeValueAsString(userStorage.getAllUsers())
        fileUsers.writeText(stringUsers, Charsets.UTF_8)

        val fileSalt = File("salt.json")
        val stringSalt = objectMapper.writeValueAsString(saltStorage.getAllSalt())
        fileSalt.writeText(stringSalt, Charsets.UTF_8)
    }
    Runtime.getRuntime().addShutdownHook(hook)

    readln()
    server.stop()
}
