package com.ioffeivan.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a standard error response structure
 * received from the API (e.g., in an error body for 4xx/5xx status codes).
 *
 * This DTO is used by Kotlinx Serialization to parse the error body into a structured object,
 * typically used within the `Result.Error` state.
 */
@Serializable
data class ErrorDto(
    @SerialName("message")
    val message: String,
)
