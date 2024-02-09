package com.disneyLand.source

import com.disneyLand.dto.DisneyCharactersList
import com.disneyLand.dto.DisneyOriginalActor
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DisneyApiService {
    @GET("/character")
    suspend fun fetchDisneyCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): DisneyCharactersList

    @GET("/character/{id}")
    suspend fun fetchDisneyCharacterById(@Path("id") id: String): DisneyOriginalActor
}
