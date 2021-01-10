package br.com.loboneto.mesamobile.data.domain.dao.auth

import java.io.Serializable

data class AuthResultError(
    val code: String,
    val message: String
) : Serializable