package com.disneyland.data.dto

import com.google.gson.annotations.SerializedName

data class DisneyActorDto(
  @SerializedName("info") var info: Info? = Info(),
  @SerializedName("data") var data: Actor? = Actor(),
)

data class Actor(
  @SerializedName("_id") var id: Int? = null,
  @SerializedName("films") var films: ArrayList<String>? = arrayListOf(),
  @SerializedName("shortFilms") var shortFilms: ArrayList<String>? = arrayListOf(),
  @SerializedName("tvShows") var tvShows: ArrayList<String>?= arrayListOf(),
  @SerializedName("videoGames") var videoGames: ArrayList<String>? = arrayListOf(),
  @SerializedName("parkAttractions") var parkAttractions: ArrayList<String>? = arrayListOf(),
  @SerializedName("allies") var allies: ArrayList<String>? = arrayListOf(),
  @SerializedName("enemies") var enemies: ArrayList<String>? = arrayListOf(),
  @SerializedName("sourceUrl") var sourceUrl: String? = null,
  @SerializedName("name") var name: String? = null,
  @SerializedName("imageUrl") var imageUrl: String? = null,
  @SerializedName("createdAt") var createdAt: String? = null,
  @SerializedName("updatedAt") var updatedAt: String? = null,
  @SerializedName("url") var url: String? = null,
  @SerializedName("__v") var _v: Int? = null,
)