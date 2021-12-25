package com.example.devops.login

import android.content.Context
import com.auth0.android.result.Credentials
import android.content.SharedPreferences
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.UserProfile

class CredentialsManager {

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        var account: Auth0? = null

        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE)
        }

        fun saveCredentials(context: Context, credentials: Credentials) {
            val editor = getSharedPreferences(context).edit()
            editor.putString(ACCESS_TOKEN, credentials.accessToken).apply()
        }

        fun deleteCredentials(context: Context) {
            val editor = getSharedPreferences(context).edit()
            editor.remove(ACCESS_TOKEN).apply()
        }

        fun getAccessToken(context: Context): String? {
            return getSharedPreferences(context).getString(ACCESS_TOKEN, null)
        }

        fun showUserProfile(accessToken: String, listener: (UserProfile?) -> Unit) {
            if (account != null) {
                val client = AuthenticationAPIClient(account!!)

                // With the access token, call `userInfo` and get the profile from Auth0.
                client.userInfo(accessToken)
                    .start(object : Callback<UserProfile, AuthenticationException> {
                        override fun onFailure(exception: AuthenticationException) {
                            // Something went wrong!
                            listener(null)
                        }

                        override fun onSuccess(profile: UserProfile) {
                            listener(profile)
                        }
                    })
            } else {
                listener(null)
            }
        }
    }
}