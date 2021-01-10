package br.com.loboneto.mesamobile.data.domain.dto

import java.io.Serializable

data class SignUpDTO(
    val name: String,
    val email: String,
    val password: String
):Serializable