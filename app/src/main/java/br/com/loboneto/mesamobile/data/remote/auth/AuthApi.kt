package br.com.loboneto.mesamobile.data.remote.auth

import br.com.loboneto.mesamobile.data.Constants
import br.com.loboneto.mesamobile.data.domain.dao.auth.SignInDAO
import br.com.loboneto.mesamobile.data.domain.dto.SignInDTO
import br.com.loboneto.mesamobile.data.domain.dto.SignUpDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST(Constants.SIGN_IN)
    suspend fun signIn(
        @Body signInDTO: SignInDTO,
    ): Response<SignInDAO>

    @POST(Constants.SIGN_UP)
    suspend fun signUp(
        @Body signUpDTO: SignUpDTO,
    ): Response<SignInDAO>
}