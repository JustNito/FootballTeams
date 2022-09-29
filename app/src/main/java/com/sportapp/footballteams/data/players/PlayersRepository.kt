package com.sportapp.footballteams.data.players

import android.util.Log
import com.sportapp.footballteams.data.players.remote.PlayerRemoteDataSource
import com.sportapp.footballteams.data.utils.ErrorHandler
import com.sportapp.footballteams.ui.model.Player
import javax.inject.Inject
import com.sportapp.footballteams.data.utils.Result

class PlayersRepository @Inject constructor(
    val playerRemoteDataSource: PlayerRemoteDataSource,
    val errorHandler: ErrorHandler
) {
    suspend fun getPlayersByTeamId(id: Int): Result<List<Player>> = try {
        Result.Success(
            playerRemoteDataSource.getPlayersByTeamId(id).response.map {
                it.player.let { player ->
                    Player(
                        name = player.name,
                        age = player.age,
                        height = player.height,
                        weight = player.weight,
                        photo = player.photo
                    )
                }
            }
        )
    } catch (e: Throwable) {
        Log.i("DataTest", "${e.message}")
        Result.Error(
            errorHandler.getError(e)
        )
    }
}
