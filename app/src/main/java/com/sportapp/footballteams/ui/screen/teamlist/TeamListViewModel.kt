package com.sportapp.footballteams.ui.screen.teamlist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sportapp.footballteams.data.country.CountryRepository
import com.sportapp.footballteams.data.team.TeamRepository
import com.sportapp.footballteams.data.utils.ErrorEntity
import com.sportapp.footballteams.data.utils.Result
import com.sportapp.footballteams.ui.model.Country
import com.sportapp.footballteams.ui.model.Team
import com.sportapp.footballteams.ui.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class TeamListViewModel(
    private val teamRepository: TeamRepository,
    private val countryRepository: CountryRepository
) : ViewModel() {

    val teams = mutableStateListOf<Team>()
    private val countries = mutableListOf<Country>()

    private var fullTeams = mutableListOf<Team>()

    private var _progress by mutableStateOf(0)
    val progress: Int
        get() = _progress

    private var _searchLine by mutableStateOf("")
    val searchLine
        get() = _searchLine

    private var _status by mutableStateOf(Status.OK)
    val status: Status
        get() = _status

    init {
        getCountries()
    }

    fun onSearchBarChange(str: String) {
        _searchLine = str

        if(_searchLine.length >= 3) {
            searchTeamByName(str)
        } else {
            teams.clear()
        }

    }

    fun getNumOfCountries() = countries.size

    private fun getCountries() = viewModelScope.launch(Dispatchers.IO) {
        when(val countriesResult = countryRepository.getCountries()) {
            is Result.Success -> {
                countries.addAll(countriesResult.data)
            }
            is Result.Error -> {
                changeStatusByError(countriesResult.error)
            }
        }
    }

    fun tryAgain() {
        if(countries.isEmpty())
            getCountries()
        searchTeamByName(_searchLine)
    }

    private fun getTeams() = viewModelScope.launch(Dispatchers.IO) {
        _status = Status.LOADING
        val teams = mutableListOf<Team>()
        when(val countriesResult = countryRepository.getCountries()) {
            is Result.Success -> {
                countries.addAll(countriesResult.data)
                countries.map { country ->
                    _progress += 1
                    when (val teamResult =
                        teamRepository.getTeamByCountry(country.name, country.flag)) {
                        is Result.Success -> {
                            teams.addAll(teamResult.data)
                        }
                        is Result.Error -> {
                            changeStatusByError(teamResult.error)
                            teams.clear()
                        }
                    }
                }
                teams.sortBy {
                    it.name
                }
                this@TeamListViewModel.teams.addAll(teams)
                fullTeams = teams
                teamRepository.insertAllTeams(teams)
                _status = Status.OK
                _progress = 0
            }
            is Result.Error -> {
                changeStatusByError(countriesResult.error)
            }
        }
    }

    private fun findCountryFlag(country: String?): String? {
        Log.i("MainTest", "$country")
        return if(country != null) {
            countries.find {
                it.name.lowercase() == country.lowercase()
            }?.flag
        } else {
            null
        }
    }

    private fun searchTeamByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        _status = Status.LOADING
        teams.clear()
        when(val result = teamRepository.getTeamsByName(name)) {
            is Result.Success -> {
                teams.addAll(
                    result.data.map {
                        it.copy(flag = findCountryFlag(it.country))
                    }
                )
                _status = Status.OK
            }
            is Result.Error -> {
                if(result.error == ErrorEntity.Unknown)
                    _status = Status.OK
                else
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