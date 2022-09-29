package com.sportapp.footballteams.data.team.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTeams(teams: List<TeamModel>)

    @Query("select exists(select 1 from teams)")
    suspend fun getCountOfTeams(): Int

    @Query("select * from teams where country = :country")
    suspend fun getTeamsByCountry(country: String): List<TeamModel>

}