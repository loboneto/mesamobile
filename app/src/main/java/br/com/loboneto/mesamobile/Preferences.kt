package br.com.loboneto.mesamobile

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Preferences(context: Context) {

    private var shared = context.getSharedPreferences(NAME, MODE_PRIVATE)
    private var editor = shared.edit()

    //auth

    fun getToken(): String {
        return shared.getString("token", "") as String
    }

    fun setToken(token: String) {
        editor.putString("token", token)
        editor.commit()
    }

    //user

    fun getName(): String {
        return shared.getString("name", "") as String
    }

    fun setName(name: String) {
        editor.putString("name", name)
        editor.commit()
    }

    fun getEmail(): String {
        return shared.getString("email", "") as String
    }

    fun setEmail(email: String) {
        editor.putString("email", email)
        editor.commit()
    }

    fun signOut() {
        setToken("")
        setName("")
        setEmail("")
    }

    companion object {
        const val NAME = "mesa.mobile"
    }
}
