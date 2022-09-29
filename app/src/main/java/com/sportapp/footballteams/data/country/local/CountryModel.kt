package com.sportapp.footballteams.data.country.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryModel(
    @PrimaryKey
    val name: String,
    val code: String?,
    val flag: String?,
)