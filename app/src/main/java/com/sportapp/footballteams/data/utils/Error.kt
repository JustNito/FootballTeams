package com.sportapp.footballteams.data.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import java.util.concurrent.CancellationException
import javax.inject.Inject

class ErrorHandler @Inject constructor() {

    fun getError(throwable: Throwable): ErrorEntity {

        return when(throwable) {
            is IOException -> ErrorEntity.Network
            is CancellationException -> ErrorEntity.CoroutineCancel
            is HttpException -> {
                when(throwable.code()){

                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied

                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable

                    else -> ErrorEntity.Unknown
                }
            } else -> ErrorEntity.Unknown
        }
    }
}
sealed class ErrorEntity {

    object Network: ErrorEntity()

    object CoroutineCancel: ErrorEntity()

    object NotFound: ErrorEntity()

    object Unknown: ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()
}