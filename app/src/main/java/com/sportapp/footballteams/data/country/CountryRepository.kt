package com.sportapp.footballteams.data.country

import android.util.Log
import com.sportapp.footballteams.data.country.local.CountryLocalDataSource
import com.sportapp.footballteams.data.country.local.CountryModel
import com.sportapp.footballteams.data.country.remote.CountryRemoteDataSource
import com.sportapp.footballteams.data.utils.ErrorHandler
import com.sportapp.footballteams.data.utils.Result
import com.sportapp.footballteams.ui.model.Country
import javax.inject.Inject

class CountryRepository @Inject constructor(
    val countryRemoteDataSource: CountryRemoteDataSource,
    val countryLocalDataSource: CountryLocalDataSource,
    val errorHandler: ErrorHandler
) {

    private suspend fun getCountOfCountries() = countryLocalDataSource.getCountOfCountries()

    suspend fun getCountries(): Result<List<Country>> = try {
        if(getCountOfCountries() != 0) {
            Result.Success(
                data = countryLocalDataSource.getAllCountries().map { country ->
                    Country(
                        name = country.name,
                        code = country.code,
                        flag = country.flag
                    )
                }
            )
        } else {
            val countries = countryRemoteDataSource.getAllCountries().response.map { country ->
                CountryModel(
                    name = country.name,
                    code = country.code,
                    flag = country.flag
                )
            }
            countryLocalDataSource.insertAllCountries(countries)
            Result.Success(
                data = countries.map { country ->
                    Country(
                        name = country.name,
                        code = country.code,
                        flag = country.flag
                    )
                 }
            )
        }
    } catch (e: Throwable) {
        Log.i("MainTest","${e.message}")
        Result.Error(
            error = errorHandler.getError(e)
        )
    }
}