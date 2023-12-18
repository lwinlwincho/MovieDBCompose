package com.lwinlwincho.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastResponse(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "name") val name: String,
    @SerialName(value = "original_name") val originalName: String,
    @SerialName(value = "profile_path") val profilePath: String?
)
