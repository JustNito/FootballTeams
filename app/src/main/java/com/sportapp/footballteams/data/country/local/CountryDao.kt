package com.sportapp.footballteams.data.country.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCountries(countries: List<CountryModel>)

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries(): List<CountryModel>

    @Query("select exists(select 1 from countries)")
    suspend fun getCountOfCountries(): Int

}