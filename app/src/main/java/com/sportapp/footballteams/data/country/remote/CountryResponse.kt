package com.sportapp.footballteams.data.country.remote

import com.sportapp.footballteams.data.team.remote.TeamEntity
import com.sportapp.footballteams.data.team.remote.VenueEntity

data class CountryResponse(
    val get: String,
    val parameters: List<String>,
    val errors: List<String>,
    val results: Int,
    val paging: Paging,
    val response: List<CountryEntity>
) {
    data class Paging(
        val current: Int,
        val total: Int
    )
}