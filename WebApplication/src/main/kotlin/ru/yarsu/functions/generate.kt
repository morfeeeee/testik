package ru.yarsu.functions

import com.fasterxml.jackson.databind.ObjectMapper
import ru.yarsu.classes.Company
import ru.yarsu.classes.Contribution
import ru.yarsu.classes.Projects
import ru.yarsu.classes.User
import java.io.File
import java.security.SecureRandom
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import kotlin.random.Random

val objectMapper: ObjectMapper = ObjectMapper().findAndRegisterModules()
val idAdmin = UUID.fromString(generateID())

fun randomDate(startDate: LocalDate, endDate: LocalDate): LocalDate {
    val randomDays = Random.nextInt(ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1)
    return startDate.plusDays(randomDays.toLong())
}

fun generateDate(tmp: Int): LocalDate {
    return if (tmp == 0){
        val startDate = LocalDate.of(2004, 1, 1)
        val endDate = LocalDate.of(2020, 12, 31)
        randomDate(startDate, endDate)
    }
    else {
        val startDate = LocalDate.of(2021, 1, 1)
        val endDate = LocalDate.of(2030, 12, 31)
        randomDate(startDate, endDate)
    }
}

fun projectsGenerate(count: Int): MutableList<Projects> {
    val name = listOf(
        "Synergy Of Innovations",
        "Eco-Life",
        "Digital Future",
        "TechnoVision",
        "Solar Path",
        "Stellar Companion",
        "Echo Of The Biosphere",
        "Future In Your Hands",
        "Quantum Leap",
        "Green City"
    )
    val nameCompany = listOf(
        "SynergyTech Innovations",
        "EcoLife Solutions",
        "Digital Horizons Inc.",
        "TechnoVision Enterprises",
        "SolarPath Technologies",
        "Stellar Innovations Co.",
        "EcoSphere Solutions",
        "FutureTech Innovations",
        "QuantumLeap Solutions",
        "GreenCity Innovations"
    )
    val emailCompany = listOf(
        "info@synergytechinnovations.com",
        "contact@ecolifesolutions.net",
        "info@digitalhorizonsinc.com",
        "contact@technovisionenterprises.org",
        "info@solarpathtechnologies.com",
        "hello@stellarinnovationsco.com",
        "info@ecospheresolutions.net",
        "contact@futuretechinnovations.com",
        "info@quantumleapsolutions.org",
        "hello@greencityinnovations.com"
    )
    val pers = listOf(
        "Екатерина Соколова",
        "Александр Петров",
        "Мария Иванова",
        "Дмитрий Смирнов",
        "Ольга Козлова",
        "Андрей Новиков",
        "Николай Кузнецов",
        "Елена Лебедева",
        "Сергей Морозов",
        "Анна Павлова"
    )
    val desc = listOf(
        "Проект 'Synergy Of Innovations' – это уникальная идея, которая объединяет в себе силу коллективного мышления и креативные подходы для создания инновационных решений. Наша цель - совместное воплощение идей, способствующих развитию общества и решению его актуальных проблем. Мы приглашаем всех, кто разделяет наше стремление к прогрессу и готов внести свой вклад в формирование будущего, основанного на взаимодействии, вдохновении и совместной работе.",
        "Проект 'Eco-Life' представляет собой инициативу, направленную на создание устойчивой и экологически ответственной общности. Мы стремимся внедрить инновационные подходы к охране окружающей среды, поощрять устойчивые жизненные практики и повышать осознанность об экологически важных вопросах. Присоединяйтесь к нам, чтобы вместе создавать благоприятное для жизни окружение и оставлять следы, которые будут способствовать сохранению природы для будущих поколений.",
        "Проект 'Digital Future' – это стратегическая инициатива, нацеленная на формирование инновационного цифрового пространства, которое будет определять развитие общества в ближайшие десятилетия. Мы объединяем экспертов, индустриальные лидеры и технологических пионеров для создания интеллектуальной инфраструктуры, развития высокотехнологичных решений и обеспечения цифровой грамотности. Присоединяйтесь к нам, чтобы вместе формировать будущее, где технологии служат человечеству, а инновации становятся движущей силой прогресса.",
        "Проект 'TechnoVision' — это стремление совместно с технологическими вдохновителями и экспертами создать инновационные решения, которые изменят наш взгляд на мир. Мы исследуем новаторские технологии и предлагаем видение будущего, где смарт-решения, искусственный интеллект и цифровые платформы помогают нам эффективнее решать повседневные задачи, улучшать качество жизни и создавать более устойчивое общество. Присоединяйтесь к нашему сообществу, чтобы вместе осуществлять свои технологические мечты и строить будущее уже сегодня.",
        "Проект 'Solar Path' — это инициатива, направленная на популяризацию и распространение солнечной энергии как ключевого ресурса для устойчивого развития. Мы собираем вокруг себя энтузиастов, специалистов и сторонников чистой энергии с целью создания более яркого и экологически чистого будущего. Наша миссия — содействовать развитию солнечных технологий, образованию общества в области возобновляемых источников энергии и увеличению доступности солнечных решений для всех. Присоединяйтесь к нам на пути к светлому, экологически чистому и энергетически независимому будущему.",
        "Проект 'Stellar Companion' — это исследовательская и образовательная инициатива, целью которой является погружение в удивительный мир космоса и популяризация астрономии среди широкой аудитории. Мы приглашаем всех, кто мечтает узнать больше о нашей Вселенной, на увлекательное путешествие сквозь звездные просторы. Наша команда экспертов и любителей астрономии совместно исследует космические объекты, обучает основам астрономии и проводит интерактивные мероприятия для всех возрастов. Присоединяйтесь к 'Звездному Спутнику', чтобы вместе покорять небесные просторы и раскрывать тайны космоса.",
        "Проект 'Echo Of The Biosphere' призван подчеркнуть важность сохранения и баланса в нашей биосфере. Мы стремимся сформировать сообщество людей, готовых активно участвовать в охране окружающей среды, обучаясь и применяя экологически чистые подходы в повседневной жизни. Наша задача - сделать экологическое сознание частью каждого человека, вдохновляя на действия в направлении устойчивого развития и заботы о природе. Присоединяйтесь к 'Эхо Биосферы', чтобы вместе создать звучащую эхо ответственности перед нашей планетой и обеспечить благополучие для будущих поколений.",
        "Проект 'Future In Your Hands' — это призыв к активному участию каждого человека в формировании своего собственного и общего будущего. Мы верим, что каждый человек имеет потенциал и возможность внести свой вклад в создание лучшего мира. Наша миссия — вдохновлять и поддерживать инициативы, способствующие развитию общества, экологической устойчивости, социальной справедливости и инноваций. Присоединяйтесь к 'Будущему в Твоих Руках', чтобы вместе формировать общее будущее, основанное на знаниях, толерантности и сотрудничестве.",
        "Проект 'Quantum Leap' — это путеводная звезда в мире технологического прогресса и научных открытий. Мы объединяем умы лучших ученых, инженеров и предпринимателей, чтобы осуществить смелые идеи и революционные технологии, способные изменить нашу жизнь к лучшему. Наша миссия — воплотить в жизнь инновационные концепции, опираясь на принципы квантовой механики и высоких технологий. Присоединяйтесь к 'Квантовому Скачку', чтобы вместе перейти на новый уровень развития и открыть новые горизонты возможностей.",
        "Проект 'Green City' – это стратегическая инициатива, посвященная созданию экологически устойчивых и жизнеспособных городских пространств. Мы призываем жителей, городские власти и бизнес-сообщество объединить усилия в создании зеленых и инновационных городских сред, где природа и человек гармонично сочетаются. Наша цель – преобразовать городские ландшафты, снизить углеродный след и создать комфортные условия для жизни всех его обитателей. Присоединяйтесь к 'Зеленому Городу', чтобы вместе сделать нашу среду обитания более здоровой, устойчивой и привлекательной."
    )
    val projects = mutableListOf<Projects>()
    for (t in 0..<count) {
        val i = t % 10
        val dateStart = generateDate(0)
        val dateEnd = generateDate(1)
        val contribut = mutableListOf<Contribution>()
        for (j in 1..100) {
            val date = randomDate(dateStart, LocalDate.of(2024, 4, 12))
            val sum = Random.nextInt(1000, 1000000).toDouble()
            val id = generateID()
            val formattedId = UUID.fromString(id)
            val idUSER = generateID()
            val formattedIdUSER = UUID.fromString(idUSER)
            val vznos = Contribution(formattedIdUSER, date, sum, formattedId)
            contribut.add(vznos)
        }
        val contributSorted = contribut.sortedByDescending { it.date }
        val dateSTART = dateStart.atTime(Random.nextInt(0, 23), Random.nextInt(0, 59))
        val dateEND = dateEnd.atTime(Random.nextInt(0, 23), Random.nextInt(0, 59))
        val sum = Random.nextInt(100000000, 1000000000).toLong()
        val person = pers[i].split(" ")
        val company = Company(nameCompany[i], person[0], person[1], emailCompany[i])
        val id = generateID()
        val formattedId = UUID.fromString(id)
        val idMan = generateID()
        val formattedIdMan = UUID.fromString(idMan)
        val project = Projects(formattedIdMan, formattedId, name[i], contributSorted.toMutableList(), company, sum, dateSTART, dateEND, desc[i])
        projects.add(project)
    }
    return projects
}

fun generateRealisticNickname(): String {
    val firstParts = listOf("Alpha", "Beta", "Gamma", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliet")
    val secondParts = listOf("Wolf", "Dragon", "Phoenix", "Tiger", "Lion", "Falcon", "Hawk", "Bear", "Eagle", "Shark")
    val numbers = (0..9999).map { it.toString().padStart(4, '0') }

    val firstPart = firstParts.random()
    val secondPart = secondParts.random()
    val number = numbers.random()

    return "$firstPart$secondPart$number"
}

fun generateRandomNicknames(count: Int): MutableList<String> {
    return MutableList(count) { generateRealisticNickname() }
}

fun generateSalt(length: Int = 100): String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:,.<>?/"
    val random = SecureRandom()
    val salt = StringBuilder(length)
    for (i in 0 until length) {
        val randomIndex = random.nextInt(charset.length)
        salt.append(charset[randomIndex])
    }
    return salt.toString()
}

fun generateListSalt(projects: List<Projects>): Map<UUID, String>{
    val mapSalt = mutableMapOf<UUID, String>()
    for (i in projects){
        for (j in i.contrib){
            mapSalt[j.idUSER] = generateSalt()
        }
        mapSalt[i.idMan] = generateSalt()
    }
    mapSalt[idAdmin] = generateSalt()
    return mapSalt
}

fun generateListUsers(projects: List<Projects>, salt: Map<UUID, String>): List<User>{
    val listUsers = mutableListOf<User>()
    val nicknames = generateRandomNicknames(1000)
    val date = LocalDateTime.now()
    for (i in projects) {
        for (j in i.contrib) {
            val hashPassword = hashPassword(nicknames[0]+salt[j.idUSER])
            val user = User(nicknames[0], hashPassword, date, j.idUSER, "investor")
            nicknames.remove(nicknames[0])
            listUsers.add(user)
        }
        val name = i.company.namePerson+i.company.surnamePerson
        val hashPassword = hashPassword(name+salt[i.idMan])
        val user = User(name, hashPassword, date, i.idMan, "holderOfCompany")
        listUsers.add(user)
    }
    val hashPassword = hashPassword("admin"+salt[idAdmin])
    val admin = User("admin", hashPassword, date, idAdmin, "admin")
    listUsers.add(admin)
    return listUsers
}

fun fileJsonProjects(count: Int): List<Projects> {
    val fileProjects = File("projects.json")
    val projectsJson = projectsGenerate(count)
    val objP = objectMapper.writeValueAsString(projectsJson)
    fileProjects.writeText(objP, Charsets.UTF_8)
    return projectsJson
}
fun fileJsonSalt(listProjects: List<Projects>): Map<UUID, String> {
    val fileSalt = File("salt.json")
    val mapSalt = generateListSalt(listProjects)
    val objP = objectMapper.writeValueAsString(mapSalt)
    fileSalt.writeText(objP, Charsets.UTF_8)
    return mapSalt
}
fun fileJsonUsers(listProjects: List<Projects>, salt: Map<UUID, String>) {
    val fileUsers = File("users.json")
    val listUsers = generateListUsers(listProjects, salt)
    val objP = objectMapper.writeValueAsString(listUsers)
    fileUsers.writeText(objP, Charsets.UTF_8)
}
