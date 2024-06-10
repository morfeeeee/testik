package ru.yarsu.classes

import org.http4k.core.Uri
import org.http4k.core.query
import org.http4k.core.removeQuery

class Paginator(
    val uri: Uri,
    val numberPage: Int,
    val countPage: Int
) {

    fun canGoPrevious(): Boolean {
        return numberPage > 1
    }

    fun previousPages(): List<Pair<Int, Uri>> {
        return (1 until numberPage).map { page ->
            page to withQueryParameter(uri,"num", page.toString())
        }
    }

    fun canGoNext(): Boolean {
        return numberPage < countPage
    }

    fun nextPages(): List<Pair<Int, Uri>> {
        return ((numberPage + 1)..countPage).map { page ->
            page to withQueryParameter(uri, "num", page.toString())
        }
    }

    fun currentPage(): Int {
        return numberPage
    }

    private fun withQueryParameter(uri: Uri, name: String, value: String): Uri {
        val newUri = uri.removeQuery(name).query(name, value)
        return newUri
    }
}
