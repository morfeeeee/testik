package ru.yarsu.formErrors

import ru.yarsu.classes.ValueErrors
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class FormErrorsProject(val name: String?, val nameCompany: String?, val namePerson: String?, val surnamePerson: String?, val email: String?, val targetSum: String?, val dateStart: LocalDateTime, val dateEnd: String?, val description: String?) {

    fun isValidDateTime(dateTimeString: String): Boolean {
        return try {
            LocalDateTime.parse(dateTimeString)
            true
        } catch (e: DateTimeParseException) {
            false
        }
    }
    fun isDateTimeAfter(dateTime1: LocalDateTime, dateTime2: LocalDateTime): Boolean {
        return dateTime1.isAfter(dateTime2)
    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return emailRegex.matches(email)
    }
    fun isNumeric(s: String): Boolean {
        return try {
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
    fun getListErrorsSum(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!isNumeric(value)){
                listErrors.add("Поле должно быть числом")
            }
            if(isNumeric(value) && value.toLong() <= 0){
                listErrors.add("Число не должно быть отрицательным или равным 0")
            }
        }
        return listErrors
    }
    fun getListErrorsString(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        return listErrors
    }
    fun getListErrorsNamePerson(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(isNumeric(value)){
                listErrors.add("Поле не должно быть числом")
            }
        }
        return listErrors
    }
    fun getListErrorsEmail(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!isValidEmail(value)){
                listErrors.add("Email введен неверно")
            }
        }
        return listErrors
    }
    fun getListErrorsDate(value1: LocalDateTime,value2: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        if(value2 == null || value2.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!isValidDateTime(value2)){
                listErrors.add("Дата введена неверно")
            }
            else {
                if (!isDateTimeAfter(LocalDateTime.parse(value2, formatter), value1)) {
                    listErrors.add("Конец сбора средств должен быть позже, чем его начало")
                }
            }
        }
        return listErrors
    }
    fun getFormErrors(): ValueErrors {
        val formErrors = mutableMapOf<String, MutableList<String>>()
        formErrors[name.orEmpty()] = getListErrorsString(name)
        formErrors[nameCompany.orEmpty()] = getListErrorsString(nameCompany)
        formErrors[namePerson.orEmpty()] = getListErrorsNamePerson(namePerson)
        formErrors[surnamePerson.orEmpty()] = getListErrorsNamePerson(surnamePerson)
        formErrors[email.orEmpty()] = getListErrorsEmail(email)
        formErrors[targetSum.orEmpty()] = getListErrorsSum(targetSum)
        formErrors[dateEnd.orEmpty()] = getListErrorsDate(dateStart, dateEnd)
        formErrors[description.orEmpty()] = getListErrorsString(description)
        return ValueErrors(formErrors)
    }
}