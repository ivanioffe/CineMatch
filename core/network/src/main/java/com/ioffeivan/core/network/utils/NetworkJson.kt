package com.ioffeivan.core.network.utils

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object NetworkJson {
    private val contentType = "application/json".toMediaType()

    val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    val converterFactory = json.asConverterFactory(contentType)
}
