package com.example.devops


import android.app.SearchManager
import android.content.Intent
import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.devops.databinding.ActivityMainBinding
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.callback.Callback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.result.UserProfile


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController,binding.drawerLayout)

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)



        binding.navView.getHeaderView(0).findViewById<Button>(R.id.ButtonLogIn).setOnClickListener(){ loginWithBrowser()}



        binding.navView.menu.findItem(R.id.ButtonLogOut).setOnMenuItemClickListener{ logout();
             true}

        account = Auth0(
            "fFPxEdQJbyPirdQcuzrSNuYiz7tp8nLL",
            "dev-g6aj--a8.us.auth0.com"
        )


        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }

    }

    private fun doMySearch(query: String) {


    }



   override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController,binding.drawerLayout)
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.
        WebAuthProvider.login(account)
            .withScheme("demo")
            // modify the scopes to enable read and write of user_metadata
            .withScope("openid profile email read:current_user update:current_user_metadata")
            // specify the audience for the Auth0 Management API
            .withAudience("https://dev-g6aj--a8.us.auth0.com/api/v2/")
            // Launch the authentication passing the callback where the results will be received
            .start(this, object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                }

                // Called when authentication completed successfully
                override fun onSuccess(credentials: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    val accessToken = credentials.accessToken
                    showUserProfile(accessToken)

                }
            })
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(this, object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    // The user has been logged out!
                    binding.navView.getHeaderView(0).findViewById<TextView>(R.id.greetings_text).text = getString(
                                            R.string.hello_text)
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }
            })
    }

    private fun showUserProfile(accessToken: String) {
        var client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    // Something went wrong!
                }

                override fun onSuccess(profile: UserProfile) {
                    // We have the user's profile!

                    val email = profile.email
                    val name = profile.name
                    binding.navView.getHeaderView(0).findViewById<TextView>(R.id.greetings_text).text = "Hello, ${name}"

                }
            })
    }

    fun getUserMetadata(userId: String, accessToken: String) {
        // Get the user ID and call the full getUser Management API endpoint, to retrieve the full profile information
        // Create the user API client using the account details and the access token from Credentials
        val usersClient = UsersAPIClient(account, accessToken)

        // Get the full user profile
        usersClient
            .getProfile(userId)
            .start(object: Callback<UserProfile, ManagementException> {
                override fun onFailure(exception: ManagementException) {
                    // Something went wrong!
                }

                override fun onSuccess(profile: UserProfile) {
                    // Retrieve the "country" field, if one appears in the metadata
                    val country = profile.getUserMetadata()["country"] as String?
                }
            })
    }

    fun patchUserMetadata(userId: String, accessToken: String) {
        // Create the UsersAPIClient with the account details
        // and the access token from the Credentials object
        val usersClient = UsersAPIClient(account, accessToken)

        // Create a map of data to update the user metadata with.
        // In this case, we're adding/updating a custom "country" field
        val metadata = mapOf("country" to "United States")

        // Call updateMetadata with the id of the user to update, and the map of data
        usersClient.updateMetadata(userId, metadata)
            .start(object: Callback<UserProfile, ManagementException> {
                override fun onFailure(exception: ManagementException) {
                    // Something went wrong!
                }

                override fun onSuccess(profile: UserProfile) {
                    // The metadata was updated and we're given the updated user profile.
                    // Retrieve the "country" field, if one appears in the metadata
                    val country = profile.getUserMetadata()["country"] as String?
                }
            })
    }

    fun trendsOnClick(item: android.view.MenuItem) {

    }
}
