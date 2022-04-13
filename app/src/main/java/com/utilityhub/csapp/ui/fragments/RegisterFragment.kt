package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.databinding.FragmentRegisterBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private var username = ""
    private var email = ""
    private var password = ""
    private var confirmPassword = ""

    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var progressBar: ContentLoadingProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            this@RegisterFragment.progressBar = this.progressBar

            btnRegister.setOnClickListener {
                validateDataAndRegister()
            }
            btnLogin.setOnClickListener {
                navigateToLogin()
            }
        }
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
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
        progressBar.show()
        viewModel.createAccount(email, password, username).observe(viewLifecycleOwner){ response ->
            when(response){
                is Response.Success -> {
                    addUserToFirestore()
                }
                is Response.Failure -> {
                    progressBar.hide()
                    print(response.errorMessage)
                }
            }
        }
    }

    private fun addUserToFirestore() {
        viewModel.addUserToFirestore().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    progressBar.hide()
                    navigateToLogin()
                }
                is Response.Failure -> {
                    progressBar.hide()
                    print(response.errorMessage)
                }
            }
        }
    }

    private fun navigateToLogin(){
        val navLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(navLogin)
    }

}