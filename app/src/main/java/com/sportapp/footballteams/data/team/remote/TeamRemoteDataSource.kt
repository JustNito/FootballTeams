package com.sportapp.footballteams.data.team.remote

import com.sportapp.footballteams.data.network.FootballApi
import com.sportapp.footballteams.ui.model.Country
import javax.inject.Inject

class TeamRemoteDataSource @Inject constructor(val footballApi: FootballApi) {
    suspend fun getTeamByCountry(country: String) = footballApi.retrofitService.getTeamByCountry(country)

    suspend fun getTeamsByName(name: String) = footballApi.retrofitService.getTeamsByName(name)
}