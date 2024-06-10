package ru.yarsu.formErrors

import ru.yarsu.classes.ValueErrors

class FormErrorsContrib(val sum: String?) {

    fun isNumeric(s: String): Boolean {
        return try {
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun getListErrors(value: String?): MutableList<String>{
        val listErrors = mutableListOf<String>()
        if(value == null || value.trim() == ""){
            listErrors.add("Поле должно быть заполнено")
        }
        else{
            if(!isNumeric(value)){
                listErrors.add("Поле должно быть числом")
            }
            if(isNumeric(value) && value.toDouble() <= 0){
                listErrors.add("Число не должно быть отрицательным или равным 0")
            }
        }
        return listErrors
    }

    fun getFormErrors(): ValueErrors {
        val formErrors = mutableMapOf<String, MutableList<String>>()
        formErrors[sum.orEmpty()] = getListErrors(sum)
        return ValueErrors(formErrors)
    }


}