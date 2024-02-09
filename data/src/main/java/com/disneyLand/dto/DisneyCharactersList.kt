package com.disneyLand.dto

import com.google.gson.annotations.SerializedName

data class DisneyCharactersList(
    @SerializedName("info") val info: Info? = Info(),
    @SerializedName("data") val data: ArrayList<Characters> = arrayListOf()
)

data class Characters(
    @SerializedName("_id") val id: Int? = null,
    @SerializedName("films") val films: ArrayList<String> = arrayListOf(),
    @SerializedName("shortFilms") val shortFilms: ArrayList<String> = arrayListOf(),
    @SerializedName("tvShows") val tvShows: ArrayList<String> = arrayListOf(),
    @SerializedName("videoGames") val videoGames: ArrayList<String> = arrayListOf(),
    @SerializedName("parkAttractions") val parkAttractions: ArrayList<String> = arrayListOf(),
    @SerializedName("allies") val allies: ArrayList<String> = arrayListOf(),
    @SerializedName("enemies") val enemies: ArrayList<String> = arrayListOf(),
    @SerializedName("sourceUrl") val sourceUrl: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("createdAt") val createdAt: String? = null,
    @SerializedName("updatedAt") val updatedAt: String? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("__v") val _v: Int? = null
)

data class Info(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("totalPages") val totalPages: Int? = null,
    @SerializedName("previousPage") val previousPage: String? = null,
    @SerializedName("nextPage") val nextPage: String? = null
)
