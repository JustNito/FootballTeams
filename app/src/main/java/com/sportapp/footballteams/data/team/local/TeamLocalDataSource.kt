package com.sportapp.footballteams.data.team.local

import com.sportapp.footballteams.data.country.local.CountryModel
import com.sportapp.footballteams.ui.model.Country
import javax.inject.Inject

class TeamLocalDataSource @Inject constructor(val teamDao: TeamDao) {

    suspend fun insertAllTeams(teams: List<TeamModel>) = teamDao.insertAllTeams(teams)

    suspend fun getCountOfTeams(): Int = teamDao.getCountOfTeams()

    suspend fun getTeamsByCountry(country: String): List<TeamModel> =
        teamDao.getTeamsByCountry(country)
}