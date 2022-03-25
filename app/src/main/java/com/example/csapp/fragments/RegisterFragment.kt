package com.example.csapp.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.R
import com.example.csapp.databinding.FragmentRegisterBinding
import com.example.csapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var username = ""
    private var email = ""
    private var password = ""
    private var confirmPassword = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnRegister.setOnClickListener {
                validateDataAndRegister()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun validateDataAndRegister() {
        binding.apply {
            username = inputUsername.text.toString().trim()
            email = inputEmail.text.toString().trim()
            password = inputPassword.text.toString().trim()
            confirmPassword = inputConfirmPassword.text.toString().trim()

            if (TextUtils.isEmpty(username)) {
                inputUsername.error = "Username is required"
                inputUsername.requestFocus()
            } else if (TextUtils.isEmpty(email)) {
                inputEmail.error = "Email is required"
                inputEmail.requestFocus()
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.error = "Invalid email format"
                inputEmail.requestFocus()
            } else if (TextUtils.isEmpty(password)) {
                inputPassword.error = "Password is required"
                inputPassword.requestFocus()
            } else if (password.length < 6) {
                inputPassword.error = "Password must have at least 6 characters"
                inputPassword.requestFocus()
            } else if (password != confirmPassword) {
                inputConfirmPassword.error = "Passwords do not match"
            } else {
                register()
            }
        }
    }

    private fun register() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.currentUser!!.updateProfile(userProfileChangeRequest {
                    displayName = username
                })
                // store user id in database
                saveUserId()
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Account already exists!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserId() {
        val currentUser = auth.currentUser!!
        val database = FirebaseFirestore.getInstance().collection(Constants.USERS)

        database.document(currentUser.uid)
            .set(HashMap<String, Any>())
            .addOnSuccessListener {
                Toast.makeText(activity, "Account created successfully!", Toast.LENGTH_SHORT).show()
                val navLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(navLogin)
            }
    }

}