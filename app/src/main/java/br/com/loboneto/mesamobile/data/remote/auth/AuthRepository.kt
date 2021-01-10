package br.com.loboneto.mesamobile.data.remote.auth

import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.domain.dao.auth.SignInDAO
import br.com.loboneto.mesamobile.data.source.MesaMobileDataSource
import br.com.loboneto.mesamobile.data.domain.dto.SignInDTO
import br.com.loboneto.mesamobile.data.domain.dto.SignUpDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepository
@Inject constructor(val mesaMobileDataSource: MesaMobileDataSource) {

    suspend fun signIn(signInDTO: SignInDTO): Flow<DataState<SignInDAO>> =
        mesaMobileDataSource.signIn(signInDTO)

    suspend fun signUp(signUpDTO: SignUpDTO): Flow<DataState<SignInDAO>> =
        mesaMobileDataSource.signUp(signUpDTO)
}