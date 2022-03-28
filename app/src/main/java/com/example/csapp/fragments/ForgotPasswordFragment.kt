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
import com.example.csapp.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment(R.layout.fragment_forgot_password) {

    private var email = ""
    private lateinit var auth : FirebaseAuth
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnResetPassword.setOnClickListener {
                validateDataAndReset()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun validateDataAndReset() {
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