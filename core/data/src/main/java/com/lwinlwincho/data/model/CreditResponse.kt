package com.lwinlwincho.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditResponse(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "cast") val cast: List<CastResponse>
)
