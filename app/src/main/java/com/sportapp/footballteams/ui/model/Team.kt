package com.sportapp.footballteams.ui.model

data class Team (
    val id: Int,
    val name: String,
    val code: String?,
    val country: String?,
    val flag: String? = null,
    val logo: String?
)