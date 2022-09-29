package com.sportapp.footballteams.ui.screen.teamlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sportapp.footballteams.data.country.CountryRepository
import com.sportapp.footballteams.data.team.TeamRepository
import javax.inject.Inject

class TeamListViewModelFactory @Inject constructor(
    val teamRepository: TeamRepository,
    val countryRepository: CountryRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TeamListViewModel(
            teamRepository = teamRepository,
            countryRepository = countryRepository
        ) as T
    }
}