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
import com.example.csapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth : FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        checkUserState()
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            validateDataAndLogin()
        }

        binding.btnContinue.setOnClickListener {
            goTo(R.id.mapsFragment)
        }

        binding.btnCreateAccount.setOnClickListener{
            goTo(R.id.registerFragment)
        }

        binding.btnForgotPassword.setOnClickListener {
           goTo(R.id.forgotPasswordFragment)
        }
    }

    private fun goTo(fragment: Int) {
        findNavController().navigate(fragment)
    }

    private fun validateDataAndLogin() {
        email = binding.inputEmail.text.toString().trim()
        password = binding.inputPassword.text.toString().trim()

        if (TextUtils.isEmpty(email)){
            binding.inputEmail.error = "Email is required"
            binding.inputEmail.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.inputEmail.error = "Please, provide a valid email"
            binding.inputEmail.requestFocus()
        }
        else if (TextUtils.isEmpty(password)){
            binding.inputPassword.error = "Password is required"
            binding.inputPassword.requestFocus()
        }
        else{
            login()
        }

    }

    private fun login() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                val loggedUser = auth.currentUser
                val email = loggedUser!!.email

                Toast.makeText(activity,"Logged in as $email",Toast.LENGTH_SHORT).show()
                goTo(R.id.profileFragment)

            }
            .addOnFailureListener {
                Toast.makeText(activity,"Login failed",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUserState() {
        val user = auth.currentUser

        if(user != null){
            //user is already logged in
            goTo(R.id.profileFragment)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}