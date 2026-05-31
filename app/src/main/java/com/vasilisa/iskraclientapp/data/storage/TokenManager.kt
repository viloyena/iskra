package com.vasilisa.iskraclientapp.data.storage

import android.content.Context
import androidx.core.content.edit

class TokenManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "auth",
            Context.MODE_PRIVATE
        )

    fun saveToken(token: String) {

        prefs.edit {
            putString("jwt", token)
        }
    }

    fun getToken(): String? {

        return prefs.getString(
            "jwt",
            null
        )
    }

    fun clearToken() {

        prefs.edit {
            remove("jwt")
        }
    }
}