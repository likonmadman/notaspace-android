package ru.notaspace.data.models

import com.google.gson.annotations.SerializedName

/**
 * Ответ на поиск
 */
data class SearchResponse(
    @SerializedName("data")
    val pages: List<Page>
)



