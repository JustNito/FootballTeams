package com.sportapp.footballteams.data.network

import com.sportapp.footballteams.data.country.remote.CountryResponse
import com.sportapp.footballteams.data.players.remote.PlayerResponse
import com.sportapp.footballteams.data.team.remote.TeamResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class FootballApi @Inject constructor(val retrofit: Retrofit) {
    interface FootballApiService {
        @GET("teams")
        suspend fun getTeamByCountry(@Query("country") country: String): TeamResponse

        @GET("teams")
        suspend fun getTeamsByName(@Query("search") name: String): TeamResponse

        @GET("teams/countries")
        suspend fun getAllCountries(): CountryResponse

        @GET("players")
        suspend fun getPlayersByTeamId(
            @Query("team") id: Int,
            @Query("season") season: Int
        ): PlayerResponse
    }
    val retrofitService: FootballApiService by lazy {
        retrofit.create(FootballApiService::class.java)
    }
}