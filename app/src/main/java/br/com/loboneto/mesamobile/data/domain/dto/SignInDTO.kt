package br.com.loboneto.mesamobile.data.domain.dto

import java.io.Serializable

data class SignInDTO(
    val email: String,
    val password: String
):Serializable