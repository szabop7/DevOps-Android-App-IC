package com.example.devops.screens.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.example.devops.R
import com.example.devops.databinding.FragmentProfileLogInBinding
import com.example.devops.login.CredentialsManager

class ProfileLogInFragment : Fragment() {
    private lateinit var binding: FragmentProfileLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_log_in, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.ButtonLogIn.setOnClickListener(){loginWithBrowser()}
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(CredentialsManager.account!!)
            .withScheme("demo")
            // modify the scopes to enable read and write of user_metadata
            .withScope("openid profile email read:current_user update:current_user_metadata")
            // specify the audience for the Auth0 Management API
            .withAudience("https://dev-g6aj--a8.us.auth0.com/api/v2/")
            // Launch the authentication passing the callback where the results will be received
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }

                // Called when authentication completed successfully
                override fun onSuccess(result: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    CredentialsManager.saveCredentials(requireContext(), result)
                }
            })
    }
}