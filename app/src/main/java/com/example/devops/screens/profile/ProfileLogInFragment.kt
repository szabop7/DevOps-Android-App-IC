package com.example.devops.screens.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.devops.MainActivity
import com.example.devops.R
import com.example.devops.databinding.FragmentProfileLogInBinding
import androidx.navigation.findNavController

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
        binding.ButtonLogIn.setOnClickListener() { sendLoginIntent() }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun sendLoginIntent() {
        if (activity != null) {
            (activity as MainActivity).loginWithBrowser {
                binding.root.findNavController().popBackStack()
            }
        }
    }
}