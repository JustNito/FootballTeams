package com.sportapp.footballteams.data.country.remote

import com.sportapp.footballteams.data.network.FootballApi
import javax.inject.Inject

class CountryRemoteDataSource @Inject constructor(val footBallApi: FootballApi) {
    suspend fun getAllCountries() = footBallApi.retrofitService.getAllCountries()
}