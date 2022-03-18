package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.csapp.R
import com.example.csapp.activities.AuthenticationActivity
import com.example.csapp.databinding.FragmentProfileBinding
import com.example.csapp.models.User
import com.example.csapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var auth : FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.apply {
            loadUserInfo(this)

            btnLogout.setOnClickListener {
                auth.signOut()
                startActivity(Intent(activity, AuthenticationActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun loadUserInfo(binding: FragmentProfileBinding) {
        val userId = auth.currentUser?.uid
        val database = FirebaseFirestore.getInstance().collection(Constants.USERS)

        if (userId != null) {
            database.document(userId).get()
                .addOnSuccessListener{
                    val userProfile = it.toObject<User>()

                    if(userProfile != null){
                        binding.apply {
                            email.text = userProfile.email.toString()
                            username.text = userProfile.username.toString()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(activity, "Failed loading the profile", Toast.LENGTH_SHORT).show()
                    binding.apply {
                        email.text = ""
                        username.text = ""
                    }
                }
        }
    }
}