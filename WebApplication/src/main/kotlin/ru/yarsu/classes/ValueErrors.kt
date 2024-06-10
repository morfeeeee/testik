package ru.yarsu.classes

class ValueErrors(val formErrors: MutableMap<String, MutableList<String>>) {

    fun isError(): Boolean{
        return getAllErrors().isNotEmpty()
    }

    fun getListErrors(value: String?): List<String>? {
        return formErrors[value]
    }
    fun getErrorsByKey(key: String): List<String>? {
        return formErrors[key]
    }
    fun getAllErrors(): List<String>{
        val listAllErrors = mutableListOf<String>()
        for(i in formErrors){
            listAllErrors += i.value
        }
        return listAllErrors.distinct()
    }
    fun addError(name: String?, error: String){
        formErrors[name]?.add(error)
    }
}