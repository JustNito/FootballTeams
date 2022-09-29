package com.sportapp.footballteams.data.team.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamModel (
    @PrimaryKey
    val id: Int,
    val name: String,
    val code: String?,
    val country: String?,
    val logo: String?
)