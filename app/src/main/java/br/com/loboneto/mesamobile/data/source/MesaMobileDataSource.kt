package br.com.loboneto.mesamobile.data.source

import br.com.loboneto.mesamobile.data.DataState
import br.com.loboneto.mesamobile.data.domain.dao.auth.SignInDAO
import br.com.loboneto.mesamobile.data.domain.dao.news.HighlightsResult
import br.com.loboneto.mesamobile.data.domain.dao.news.NewsResult
import br.com.loboneto.mesamobile.data.domain.dto.SignInDTO
import br.com.loboneto.mesamobile.data.domain.dto.SignUpDTO
import br.com.loboneto.mesamobile.data.remote.auth.AuthApi
import br.com.loboneto.mesamobile.data.remote.news.NewsApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MesaMobileDataSource
@Inject constructor(
    private val authApi: AuthApi,
    private val newsApi: NewsApi
) {
    suspend fun signIn(signInDTO: SignInDTO) = flow<DataState<SignInDAO>> {
        emit(DataState.Loading)
        val request = authApi.signIn(signInDTO)
        val body = request.body()
        if (request.isSuccessful && body != null) {
            if (request.body() != null) {
                emit(DataState.Success(body))
            } else {
                emit(DataState.Error(request.code(), "Erro ao obter dados do usuário"))
            }
        } else {
            emit(DataState.Error(request.code(), "Erro ao realizar login"))
        }
    }.catch {
        emit(DataState.Failure(it))
    }

    suspend fun signUp(signUpDTO: SignUpDTO) = flow<DataState<SignInDAO>> {
        emit(DataState.Loading)
        val request = authApi.signUp(signUpDTO)
        val body = request.body()
        if (request.isSuccessful && body != null) {
            if (request.body() != null) {
                emit(DataState.Success(body))
            } else {
                emit(DataState.Error(request.code(), "Erro ao obter dados do usuário"))
            }
        } else {
            emit(DataState.Error(request.code(), "Erro ao realizar cadastro"))
        }
    }.catch {
        emit(DataState.Failure(it))
    }

    suspend fun fetchNews(filter: HashMap<String, Any>, token: String) = flow<DataState<NewsResult>> {
        emit(DataState.Loading)
        val request = newsApi.fetchNews(filter, token)
        val body = request.body()
        if (request.isSuccessful && body != null) {
            if (request.body() != null) {
                emit(DataState.Success(body))
            } else {
                emit(DataState.Error(request.code(), "Erro ao obter lista de notícias"))
            }
        } else {
            emit(DataState.Error(request.code(), "Erro ao solicitar lista de notícias"))
        }
    }.catch {
        emit(DataState.Failure(it))
    }

    suspend fun fetchHighlights(token: String) = flow<DataState<HighlightsResult>> {
        emit(DataState.Loading)
        val request = newsApi.fetchHighlights(token)
        val body = request.body()
        if (request.isSuccessful && body != null) {
            if (request.body() != null) {
                emit(DataState.Success(body))
            } else {
                emit(DataState.Error(request.code(), "Erro ao obter lista de notícias"))
            }
        } else {
            emit(DataState.Error(request.code(), "Erro ao solicitar lista de notícias"))
        }
    }.catch {
        emit(DataState.Failure(it))
    }
}