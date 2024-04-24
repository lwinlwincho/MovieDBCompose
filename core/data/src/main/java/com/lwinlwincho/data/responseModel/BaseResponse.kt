package com.lwinlwincho.data.responseModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<N>(
    @SerialName(value = "page")val page: Int,
    @SerialName(value = "results")val results: List<N>,
    @SerialName(value = "total_pages")val totalPages: Int,
    @SerialName(value ="total_results")val totalResults: Int
)

