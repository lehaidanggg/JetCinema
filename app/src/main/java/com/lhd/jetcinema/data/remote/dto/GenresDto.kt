package com.lhd.jetcinema.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.lhd.jetcinema.domain.model.Genre

data class GenresDto(
    @SerializedName("genres")
    val genres: List<GenreDto>
)

data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) {
    fun toDomain(): Genre {
        return Genre(id = id, name = name)
    }
}