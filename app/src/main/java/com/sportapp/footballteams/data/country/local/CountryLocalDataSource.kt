package com.sportapp.footballteams.data.country.local

import javax.inject.Inject

class CountryLocalDataSource @Inject constructor(val countryDao: CountryDao) {

    suspend fun getAllCountries() = countryDao.getAllCountries()

    suspend fun getCountOfCountries() = countryDao.getCountOfCountries()

    suspend fun insertAllCountries(countries: List<CountryModel>) =
        countryDao.insertAllCountries(countries)
}