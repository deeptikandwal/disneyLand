package com.disneyLand.source

import com.disneyLand.dto.DisneyActorDto
import com.disneyLand.dto.DisneyCharactersListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DisneyApiService {
    @GET("/character")
    suspend fun fetchDisneyCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): DisneyCharactersListDto

    @GET("/character/{id}")
    suspend fun fetchDisneyCharacterById(@Path("id") id: String): DisneyActorDto
}
