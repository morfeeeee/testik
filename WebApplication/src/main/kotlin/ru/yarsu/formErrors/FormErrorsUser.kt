package ru.yarsu.formErrors

import ru.yarsu.classes.ValueErrors

class FormErrorsUser(val nameUser: String?, val password1: String?, val password2: String?) {

    fun containsSpaces(input: String): Boolean {
        return input.contains(' ')
    }

    fun correctPassword(value1: String?, value2: String?): Boolean{
        return value1 == value2
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
            if (!correctPassword(password1, password2)) {
                listErrors.add("Пароли не совпадают")
            }
        }
        return listErrors
    }

    fun getFormErrors(): ValueErrors {
        val formErrors = mutableMapOf<String, MutableList<String>>()
        formErrors[nameUser.orEmpty()] = getListErrorsName(nameUser)
        formErrors[password1.orEmpty()] = getListErrorsPassword(password1)
        formErrors[password2.orEmpty()] = getListErrorsPassword(password2)
        return ValueErrors(formErrors)
    }


}