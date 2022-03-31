package com.utilityhub.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        getUserProfile()

        binding.apply {
            btnLogout.setOnClickListener {
                auth.signOut()
                val navAuth = ProfileFragmentDirections.actionHomeToAuthentication()
                findNavController().navigate(navAuth)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getUserProfile() {
        val currentUser = auth.currentUser
        binding.apply {
            if (currentUser != null) {
                email.text = currentUser.email
                username.text = currentUser.displayName
            } else {
                email.text = ""
                username.text = ""
            }
        }
    }
}