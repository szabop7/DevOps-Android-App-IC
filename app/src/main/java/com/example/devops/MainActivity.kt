package com.example.devops

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.devops.databinding.ActivityMainBinding
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.callback.Callback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.result.UserProfile
import com.example.devops.login.CredentialsManager
import com.example.devops.screens.bindingutils.setImageUrlAsImageView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var account: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
        setupLoginUI()
        setupAuth()
    }

    private fun setupNavigation() {
        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)
    }

    private fun setupAuth() {
        account = Auth0(
            getString(R.string.com_auth0_client_id),
            getString(R.string.com_auth0_domain)
        )
        CredentialsManager.account = account
        showNavBarUserData()
    }

    private fun setupLoginUI() {
        binding.navView.getHeaderView(0).findViewById<Button>(R.id.ButtonLogIn).setOnClickListener() { loginWithBrowser {} }
        binding.navView.menu.findItem(R.id.ButtonLogOut).setOnMenuItemClickListener { logout()
            true }

        logButtonsVisibilityToggle(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    fun loginWithBrowser(onSuccess: () -> Unit) {
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
                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
                }

                // Called when authentication completed successfully
                override fun onSuccess(result: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    CredentialsManager.saveCredentials(applicationContext, result)
                    showNavBarUserData()
                    onSuccess()
                }
            })
    }

    private fun showNavBarUserData() {
        val token = CredentialsManager.getAccessToken(applicationContext)
        if (token != null) {
            // checking if the token works...
            CredentialsManager.showUserProfile(token) {
                // We have the user's profile!
                if (it != null) {
                    binding.navView.getHeaderView(0).run {
                        findViewById<TextView>(R.id.greetings_text).text = getString(R.string.hello_username, it.name)
                        if (it.pictureURL != null) {
                            setImageUrlAsImageView(applicationContext,
                                findViewById(R.id.userProfilePic),
                                it.pictureURL
                            )
                        }
                    }

                    logButtonsVisibilityToggle(false)
                } else {
                    /* If we can't get profile data when having a user token, clear it
                        User will need to login again */
                    CredentialsManager.deleteCredentials(applicationContext)
                    logButtonsVisibilityToggle(true)
                }
            }
        } else {
            Toast.makeText(applicationContext, "Token doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(this, object : Callback<Void?, AuthenticationException> {
                override fun onSuccess(payload: Void?) {
                    CredentialsManager.deleteCredentials(applicationContext)
                    // The user has been logged out!
                    binding.navView.getHeaderView(0).findViewById<TextView>(R.id.greetings_text).text = getString(
                        R.string.hello_text)
                    logButtonsVisibilityToggle(true)
                }

                override fun onFailure(error: AuthenticationException) {
                    // Something went wrong!
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
            .start(object : Callback<UserProfile, ManagementException> {
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
            .start(object : Callback<UserProfile, ManagementException> {
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

    fun logButtonsVisibilityToggle(bool: Boolean) {
        var buttonVisLogIn: Int = View.VISIBLE
        var profilePictureVis: Int = View.INVISIBLE

        if (!bool) {
            buttonVisLogIn = View.INVISIBLE
            profilePictureVis = View.VISIBLE
        }
        binding.navView.getHeaderView(0).run {
            findViewById<Button>(R.id.ButtonLogIn).visibility = buttonVisLogIn
            findViewById<ImageView>(R.id.userProfilePic).visibility = profilePictureVis
        }
        binding.navView.menu.run {
            findItem(R.id.ProfileFragment).isVisible = !bool
            findItem(R.id.ButtonLogOut).isVisible = !bool
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
