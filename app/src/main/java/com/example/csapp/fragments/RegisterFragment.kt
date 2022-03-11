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
import com.example.csapp.models.User
import com.example.csapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    private var username = ""
    private var email = ""
    private var password = ""
    private var confirmPassword = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            validateDataAndRegister()
        }
    }

    private fun validateDataAndRegister() {
        username = binding.inputUsername.text.toString().trim()
        email = binding.inputEmail.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()
        confirmPassword = binding.inputConfirmPassword.text.toString().trim()

        if (TextUtils.isEmpty(username)){
            binding.inputUsername.error = "Username is required"
            binding.inputUsername.requestFocus()
        }
        else if (TextUtils.isEmpty(email)){
            binding.inputEmail.error = "Email is required"
            binding.inputEmail.requestFocus()
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.inputEmail.error = "Invalid email format"
            binding.inputEmail.requestFocus()
        }
        else if (TextUtils.isEmpty(password)){
            binding.inputPassword.error = "Password is required"
            binding.inputPassword.requestFocus()
        }
        else if (password.length < 6){
            binding.inputPassword.error = "Password must have at least 6 characters"
            binding.inputPassword.requestFocus()
        }
        else if (password != confirmPassword){
            binding.inputConfirmPassword.error = "Passwords do not match"
        }
        else{
            register()
        }

    }

    private fun register() {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //add user to database
                saveUserInfo()
            }
            .addOnFailureListener {
                Toast.makeText(activity,"Account already exists!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveUserInfo() {
        val uid = auth.uid!!
        val database = FirebaseFirestore.getInstance().collection("Users")
        val user = User(email, username)

        database.document(uid)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(activity,"Account created successfully!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.loginFragment)
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}