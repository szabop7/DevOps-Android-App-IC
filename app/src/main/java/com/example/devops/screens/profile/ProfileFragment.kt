package com.example.devops.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.auth0.android.result.UserProfile
import com.example.devops.R
import com.example.devops.login.CredentialsManager
import com.example.devops.screens.bindingutils.setImageUrlAsImageView

class ProfileFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUserData()
    }

    private fun navigateToLoginFragment() {
        view?.findNavController()?.navigate(R.id.ProfileLogInFragment)
    }

    private fun updateUserData() {
        val accessToken: String? = CredentialsManager.getAccessToken(requireContext())
        if (accessToken == null) {
            navigateToLoginFragment()
        } else {
            CredentialsManager.showUserProfile(accessToken) {
                // We have the user's profile!
                if (it != null) {
                    showUserData(it)
                } else {
                    navigateToLoginFragment()
                }
            }
        }
    }

    private fun showUserData(userProfile: UserProfile) {
        if (view != null) {
            val v = requireView()
            val email = userProfile.email
            val name = buildDisplayName(
                userProfile.name,
                userProfile.givenName,
                userProfile.nickname,
                userProfile.familyName
            )
            v.findViewById<TextView>(R.id.artistName).text = name
            v.findViewById<Button>(R.id.contactButton).text = email
            setImageUrlAsImageView(requireContext(),
                v.findViewById(R.id.userPicture),
                userProfile.pictureURL
            )
        }
    }

    private fun buildDisplayName(givenName: String?, name: String?, nickname: String?, familyName: String?): String {
        return if (givenName != null && givenName.isNotEmpty()) {
            givenName
        } else if (name != null && name.isNotEmpty()) {
            name
        } else if (nickname != null && nickname.isNotEmpty()) {
            nickname
        } else if (familyName != null && familyName.isNotEmpty()) {
            familyName
        } else ":)"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}