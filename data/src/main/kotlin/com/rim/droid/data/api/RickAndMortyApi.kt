package com.rim.droid.data.api

import com.rim.droid.data.dto.CharacterDto
import com.rim.droid.data.dto.EpisodeDto
import com.rim.droid.data.dto.LocationDto
import com.rim.droid.data.dto.PagedResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): PagedResponseDto<CharacterDto>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDto

    @GET("episode")
    suspend fun getEpisodes(@Query("page") page: Int): PagedResponseDto<EpisodeDto>

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): EpisodeDto

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): PagedResponseDto<LocationDto>

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): LocationDto
}
