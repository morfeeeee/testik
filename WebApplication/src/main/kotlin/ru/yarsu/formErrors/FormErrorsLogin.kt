package ru.yarsu.formErrors

import ru.yarsu.classes.ValueErrors

class FormErrorsLogin(val nameUser: String?, val password: String?) {

    fun containsSpaces(input: String): Boolean {
        return input.contains(' ')
    }

    fun getListErrorsName(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else {
            if (containsSpaces(value)) {
                listErrors.add("Поле не должно содержать пробелы")
            }
        }
        return listErrors
    }
    fun getListErrorsPassword(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else {
            if (containsSpaces(value)) {
                listErrors.add("Пароль не должен содержать пробелы")
            }
        }
        return listErrors
    }

    fun getFormErrors(): ValueErrors {
        val formErrors = mutableMapOf<String, MutableList<String>>()
        formErrors[nameUser.orEmpty()] = getListErrorsName(nameUser)
        formErrors[password.orEmpty()] = getListErrorsPassword(password)
        return ValueErrors(formErrors)
    }


}