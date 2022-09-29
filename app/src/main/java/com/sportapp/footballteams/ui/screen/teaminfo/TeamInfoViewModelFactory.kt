package com.sportapp.footballteams.ui.screen.teaminfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sportapp.footballteams.data.players.PlayersRepository
import javax.inject.Inject

class TeamInfoViewModelFactory @Inject constructor(
    val playersRepository: PlayersRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TeamInfoViewModel(playersRepository = playersRepository) as T
    }
}