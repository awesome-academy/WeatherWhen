package com.thailam.weatherwhen.data.model

/**
 *  enum class to represent status of network call objects
 * SUCCESS : indicates that the getting data is successful
 * LOADING : indicates that the getting data is loading
 * ERROR : indicates that the getting data get produces error
 */
enum class Status {
    SUCCESS,
    LOADING,
    ERROR
}

/**
 * Wrapper class to include the status (SUCCESS, LOADING, ERROR) with the object response from network call
 */
data class Response<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Response<T> =
            Response(Status.SUCCESS, data, null)

        fun <T> loading(): Response<T> =
            Response(Status.LOADING, null, null)

        fun <T> error(msg: String?): Response<T> =
            Response(Status.ERROR, null, msg)
    }
}
