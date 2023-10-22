package com.example.model

import kotlinx.serialization.SerialName

data class CreditModel(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "cast") val cast: List<CastModel>
)
