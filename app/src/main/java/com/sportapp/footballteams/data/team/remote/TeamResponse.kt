package com.sportapp.footballteams.data.team.remote

data class TeamResponse(
    val get: String,
    val errors: List<String>,
    val results: Int,
    val paging: Paging,
    val response: List<Response>
) {
    data class Paging(
        val current: Int,
        val total: Int
    )
    data class Response(
        val team: TeamEntity,
        //val venue: VenueEntity
    )
}