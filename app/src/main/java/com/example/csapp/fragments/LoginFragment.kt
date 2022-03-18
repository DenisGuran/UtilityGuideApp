package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.csapp.R
import com.example.csapp.activities.MainActivity
import com.example.csapp.databinding.FragmentLoginBinding
import com.example.csapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var backPressedTime: Long = 0L
    private lateinit var backPressedToast: Toast

    private lateinit var auth : FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        checkUserState()

        binding.apply {
            btnLogin.setOnClickListener {
                validateDataAndLogin(this)
            }

            btnContinue.setOnClickListener {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }

            btnCreateAccount.setOnClickListener{
                findNavController().navigate(R.id.registerFragment)
            }

            btnForgotPassword.setOnClickListener {
                findNavController().navigate(R.id.forgotPasswordFragment)
            }
        }

        doubleTapToExit()

    }

    private fun doubleTapToExit() {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(backPressedTime + Constants.EXIT_TIME > System.currentTimeMillis()){
                    backPressedToast.cancel()
                    activity?.finish()
                    return
                }else{
                    backPressedToast = Toast.makeText(activity, "Press back again to exit", Toast.LENGTH_SHORT)
                    backPressedToast.show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })
    }

    private fun validateDataAndLogin(binding: FragmentLoginBinding) {
        binding.apply {
            email = inputEmail.text.toString().trim()
            password = inputPassword.text.toString().trim()

            if (TextUtils.isEmpty(email)){
                inputEmail.error = "Email is required"
                inputEmail.requestFocus()
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                inputEmail.error = "Please, provide a valid email"
                inputEmail.requestFocus()
            }
            else if (TextUtils.isEmpty(password)){
                inputPassword.error = "Password is required"
                inputPassword.requestFocus()
            }
            else{
                login()
            }
        }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Toast.makeText(activity,"Login successful",Toast.LENGTH_SHORT).show()
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
            .addOnFailureListener {
                Toast.makeText(activity,"Login failed",Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUserState() {
        if(auth.currentUser != null){
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }

}