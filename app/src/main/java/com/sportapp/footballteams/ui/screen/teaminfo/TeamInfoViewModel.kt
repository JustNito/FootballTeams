package com.sportapp.footballteams.ui.screen.teaminfo

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sportapp.footballteams.data.players.PlayersRepository
import com.sportapp.footballteams.data.utils.ErrorEntity
import com.sportapp.footballteams.data.utils.Result
import com.sportapp.footballteams.ui.model.Player
import com.sportapp.footballteams.ui.model.Team
import com.sportapp.footballteams.ui.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TeamInfoViewModel @Inject constructor(val playersRepository: PlayersRepository) : ViewModel() {

    private var _status by mutableStateOf(Status.LOADING)
    val status: Status
        get() = _status

    val players = mutableListOf<Player>()

    private var _team by mutableStateOf(
        Team(0,"None","None","None",null,null)
    )
    val team: Team
        get() = _team

    fun initInfo(team: Team) {
        clearInfo()
        _team = team
        getPlayersById(team.id)
    }

    private fun clearInfo() {
        players.clear()
    }

    fun tryAgain() {
        initInfo(_team)
    }

    private fun getPlayersById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        _status = Status.LOADING
        when(val result = playersRepository.getPlayersByTeamId(id)) {
            is Result.Success -> {
                players.addAll(result.data)
                Log.i("MainTest", "${result.data}")
                _status = Status.OK
            }
            is Result.Error -> {
                changeStatusByError(result.error)
            }
        }
    }

    private fun changeStatusByError(error: ErrorEntity) {
        _status = when (error) {
            ErrorEntity.Unknown -> Status.UNKNOWN
            ErrorEntity.AccessDenied -> Status.ACCESS_DENIED
            ErrorEntity.ServiceUnavailable -> Status.UNAVAILABLE
            ErrorEntity.Network -> Status.NETWORK
            ErrorEntity.CoroutineCancel -> Status.UNKNOWN
            ErrorEntity.NotFound -> Status.NOT_FOUND
        }
    }
}