package com.sportapp.footballteams.data.players.remote

import com.sportapp.footballteams.data.network.FootballApi
import javax.inject.Inject

class PlayerRemoteDataSource @Inject constructor(val footballApi: FootballApi) {

    suspend fun getPlayersByTeamId(id: Int) = footballApi.retrofitService.getPlayersByTeamId(id, 2022)
}