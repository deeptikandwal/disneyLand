package com.disneyland.data.source

import com.disneyland.data.dto.CharactersDto
import com.disneyland.data.dto.DisneyActorDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DisneyApiService {
    @GET("/character")
    suspend fun fetchDisneyCharacters(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): CharactersDto

    @GET("/character/{id}")
    suspend fun fetchDisneyCharacterById(@Path("id") id: String): DisneyActorDto
}