package com.example.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.R
import com.example.csapp.databinding.FragmentProfileBinding
import com.example.csapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        loadUserInfo()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    private fun loadUserInfo() {
        val userId = auth.currentUser!!.uid
        val database = FirebaseFirestore.getInstance().collection("Users")

        database.document(userId).get()
            .addOnSuccessListener{
                val userProfile = it.toObject<User>()

                if(userProfile != null){
                    binding.email.text = userProfile.email.toString()
                    binding.username.text = userProfile.username.toString()
                }
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed loading the profile", Toast.LENGTH_SHORT).show()
            }

    }
}