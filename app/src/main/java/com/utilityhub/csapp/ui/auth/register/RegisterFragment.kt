package com.utilityhub.csapp.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.databinding.FragmentRegisterBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private var username = ""
    private var email = ""
    private var password = ""
    private var confirmedPassword = ""

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
        val context = requireContext()
        binding.apply {
            username = inputUsername.text.toString()
            email = inputEmail.text.toString()
            password = inputPassword.text.toString()
            confirmedPassword = inputConfirmPassword.text.toString()

            val result = viewModel.validateRegistrationForm(
                username = username,
                email = email,
                password = password,
                confirmedPassword = confirmedPassword
            )

            layoutEmail.helperText = result.emailError?.asString(context)
            layoutPassword.helperText = result.passwordError?.asString(context)
            layoutUsername.helperText = result.usernameError?.asString(context)
            layoutConfirmPassword.helperText =
                result.confirmedPasswordError?.asString(context)
            if (result.hasNoError) {
                register()
            }
        }
    }

    private fun register() {
        progressBar.show()
        viewModel.createAccount(email, password, username).observe(viewLifecycleOwner) { response ->
            when (response) {
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

    private fun navigateToLogin() {
        val navLogin = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(navLogin)
    }

}