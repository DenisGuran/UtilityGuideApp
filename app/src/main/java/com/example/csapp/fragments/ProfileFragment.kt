package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.csapp.R
import com.example.csapp.activities.AuthenticationActivity
import com.example.csapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var auth : FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.apply {
            getUserProfile(this)

            btnLogout.setOnClickListener {
                auth.signOut()
                startActivity(Intent(activity, AuthenticationActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun getUserProfile(binding: FragmentProfileBinding) {
        val currentUser = auth.currentUser!!
        binding.apply {
            email.text = currentUser.email
            username.text = currentUser.displayName
        }
    }
}