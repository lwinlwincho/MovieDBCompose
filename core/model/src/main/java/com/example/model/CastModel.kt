package com.example.model

import kotlinx.serialization.SerialName

data class CastModel(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "name") val name: String,
    @SerialName(value = "original_name") val originalName: String,
    @SerialName(value = "profile_path") val profilePath: String?
)
