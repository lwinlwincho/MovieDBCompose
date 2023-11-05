package com.lwinlwincho.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    @SerialName(value =  "id")val id: Long,
    @SerialName(value =  "name")val name: String
)
