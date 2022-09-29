package com.sportapp.footballteams.data.team.remote

data class TeamEntity(
    val id: Int,
    val name: String,
    val code: String?,
    val country: String?,
    val founded: Int?,
    val national: Boolean?,
    val logo: String?
)