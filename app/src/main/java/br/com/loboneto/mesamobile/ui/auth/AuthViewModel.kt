package br.com.loboneto.mesamobile.ui.auth

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import br.com.loboneto.mesamobile.Preferences
import br.com.loboneto.mesamobile.data.domain.dto.SignInDTO
import br.com.loboneto.mesamobile.data.domain.dto.SignUpDTO
import br.com.loboneto.mesamobile.data.remote.auth.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext

class AuthViewModel
@ViewModelInject
constructor(
    @ApplicationContext context: Context,
    private val authRepository: AuthRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val preferences = Preferences(context)

    fun saveUserData(token: String, signInDTO: SignInDTO) {
        preferences.setToken(token)
        preferences.setEmail(signInDTO.email)
    }

    fun saveUserData(token: String, signUpDTO: SignUpDTO) {
        preferences.setToken(token)
        preferences.setName(signUpDTO.name)
        preferences.setEmail(signUpDTO.email)
    }

    fun signIn(signInDTO: SignInDTO) = liveData {
        emitSource(authRepository.signIn(signInDTO).asLiveData())
    }

    fun signUp(signUpDTO: SignUpDTO) = liveData {
        emitSource(authRepository.signUp(signUpDTO).asLiveData())
    }
}