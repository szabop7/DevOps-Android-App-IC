package com.example.devops.screens.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.devops.R
import com.example.devops.login.CredentialsManager
import com.example.devops.network.BASE_URL

class ProfileFragment : Fragment() {
    private fun buildDisplayName(name: String?, givenName: String?, displayName: String?, familyName: String?): String {
        return if (name != null && name.isNotEmpty()) {
            name
        } else if (givenName != null && givenName.isNotEmpty()) {
            givenName
        } else if (displayName != null && displayName.isNotEmpty()) {
            displayName
        } else if (familyName != null && familyName.isNotEmpty()) {
            familyName
        } else ":)"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accessToken: String? = CredentialsManager.getAccessToken(requireContext())
        if (accessToken == null) {
            view.findNavController().navigate(R.id.ProfileLogInFragment)
        } else {
            CredentialsManager.showUserProfile(accessToken) {
                // We have the user's profile!
                if (it != null) {
                    val email = it.email
                    val name = buildDisplayName(it.nickname, it.givenName, it.name, it.familyName)
                    view.findViewById<TextView>(R.id.artistName).text = name
                    view.findViewById<Button>(R.id.contactButton).text = email
                    if (it.pictureURL != null) {

                        val imageUrl = it.pictureURL!!
                        val imgUri = imageUrl.toUri().buildUpon()?.scheme("http")?.build()
                        Glide.with(requireContext())
                            .load(imgUri)
                            .dontAnimate()
                            .into(view.findViewById<ImageView>(R.id.userPicture))
                    }
                }
            }
        }
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