package com.example.csapp.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.R
import com.example.csapp.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private lateinit var auth : FirebaseAuth

    private var email = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentForgotPasswordBinding.bind(view)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnResetPassword.setOnClickListener {
                validateDataAndReset(this)
            }
        }
    }

    private fun validateDataAndReset(binding: FragmentForgotPasswordBinding) {
        binding.apply {
            email = inputEmail.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                inputEmail.error = "Email is required"
                inputEmail.requestFocus()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                inputEmail.error = "Please, provide a valid email"
                inputEmail.requestFocus()
            }
            else{
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Toast.makeText(activity, "Check your email to reset the password", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Email is not registered", Toast.LENGTH_SHORT).show()
            }
    }

}