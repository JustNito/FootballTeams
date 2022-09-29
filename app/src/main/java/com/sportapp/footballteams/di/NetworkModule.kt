package com.sportapp.footballteams.di

import com.sportapp.footballteams.data.network.FootBallApiInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(FootBallApiInfo.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val url = original
                .url
                .newBuilder()
                .build()
            chain.proceed(
                original
                    .newBuilder()
                    .url(url)
                    .addHeader("X-RapidAPI-Key", FootBallApiInfo.API_KEY)
                    .build()
            )
        }
        return okHttpClient.build()
    }
}