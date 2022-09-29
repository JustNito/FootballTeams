package com.sportapp.footballteams.data.players.remote

data class PlayerResponse(
    val get: String,
    val results: Int,
    val paging: Paging,
    val response: List<Response>
) {
    data class Paging(
        val current: Int,
        val total: Int
    )
    data class Response(
        val player: PlayerEntity,
    )
}
