package com.ioffeivan.core.network.utils

import okhttp3.Request

internal fun Request.Builder.authorizationHeader(token: String): Request.Builder =
    header("Authorization", "Bearer $token")
