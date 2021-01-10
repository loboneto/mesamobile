package br.com.loboneto.mesamobile.data.domain.dao

import java.io.Serializable

data class UserDAO(
    val name: String,
    val email: String
) : Serializable {

    fun userName(): String {
        return if (name.isNotEmpty()) name else "Sem acesso ao nome do usuário"
    }

    fun userEmail(): String {
        return if (email.isNotEmpty()) name else "Sem acesso ao nome do usuário"
    }
}