package com.sportapp.footballteams.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sportapp.footballteams.data.country.local.CountryDao
import com.sportapp.footballteams.data.country.local.CountryModel
import com.sportapp.footballteams.data.team.local.TeamDao
import com.sportapp.footballteams.data.team.local.TeamModel

@Database(entities = [CountryModel::class, TeamModel::class], version = 1, exportSchema = false)
abstract class FootballDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun teamDao(): TeamDao
}