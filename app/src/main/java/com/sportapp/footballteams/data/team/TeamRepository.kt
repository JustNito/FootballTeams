package com.sportapp.footballteams.data.team

import android.util.Log
import com.sportapp.footballteams.data.team.local.TeamLocalDataSource
import com.sportapp.footballteams.data.team.local.TeamModel
import com.sportapp.footballteams.data.team.remote.TeamEntity
import com.sportapp.footballteams.data.team.remote.TeamRemoteDataSource
import com.sportapp.footballteams.data.utils.ErrorHandler
import com.sportapp.footballteams.data.utils.Result
import com.sportapp.footballteams.ui.model.Team
import javax.inject.Inject

class TeamRepository @Inject constructor(
    val teamRemoteDataSource: TeamRemoteDataSource,
    val teamLocalDataSource: TeamLocalDataSource,
    val errorHandler: ErrorHandler
) {
    suspend fun getTeamByCountry(country: String, flag: String?): Result<List<Team>> = try {
        if(teamLocalDataSource.getCountOfTeams() != 0) {
            Result.Success(
                data = teamLocalDataSource.getTeamsByCountry(country).map {
                    Team(
                        id = it.id,
                        name = it.name,
                        country = it.country,
                        code = it.code,
                        flag = flag,
                        logo = it.logo,
                    )
                }
            )
        } else {
            Result.Success(
                data = teamRemoteDataSource.getTeamByCountry(country).response.map {
                    it.team.let { team ->
                        Team(
                            id = team.id,
                            name = team.name,
                            country = team.country,
                            code = team.code,
                            flag = flag,
                            logo = team.logo
                        )
                    }
                }
            )
        }
    } catch(e: Throwable) {
        Log.i("MainTest", "${e.message}")
        Result.Error(
            error = errorHandler.getError(e)
        )
    }

    suspend fun getTeamsByName(name: String): Result<List<Team>> = try {
        Result.Success(
            data = teamRemoteDataSource.getTeamsByName(name).response.map {
                it.team.let { team ->
                    Team(
                        id = team.id,
                        name = team.name,
                        country = team.country,
                        code = team.code,
                        flag = null,
                        logo = team.logo
                    )
                }
            }
        )
    } catch (e: Throwable) {
        Result.Error(
            error = errorHandler.getError(e)
        )
    }

    suspend fun insertAllTeams(teams: List<Team>) {
        if (teamLocalDataSource.getCountOfTeams() == 0) {
            teamLocalDataSource.insertAllTeams(
                teams.map {
                    TeamModel(
                        id = it.id,
                        name = it.name,
                        country = it.country,
                        code = it.code,
                        logo = it.logo,
                    )
                }
            )
        }
    }
}