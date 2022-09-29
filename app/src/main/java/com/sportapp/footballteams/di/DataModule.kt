package com.sportapp.footballteams.di

import android.content.Context
import androidx.room.Room
import com.sportapp.footballteams.data.country.local.CountryDao
import com.sportapp.footballteams.data.db.FootballDatabase
import com.sportapp.footballteams.data.team.local.TeamDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): FootballDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            FootballDatabase::class.java,
            "football_database"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideCountriesDao(db: FootballDatabase): CountryDao {
        return db.countryDao()
    }

    @Provides
    fun provideTeamDao(db: FootballDatabase): TeamDao = db.teamDao()
}