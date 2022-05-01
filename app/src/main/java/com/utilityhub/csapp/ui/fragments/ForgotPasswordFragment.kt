package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import com.utilityhub.csapp.databinding.FragmentForgotPasswordBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.viewmodels.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment :
    BaseFragment<FragmentForgotPasswordBinding>(FragmentForgotPasswordBinding::inflate) {

    private var email = ""

    private val viewModel by viewModels<ForgotPasswordViewModel>()
    private lateinit var progressBar: ContentLoadingProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            this@ForgotPasswordFragment.progressBar = this.progressBar

            btnResetPassword.setOnClickListener {
                validateEmailAndReset()
            }
        }
    }

    private fun validateEmailAndReset() {
        binding.apply {
            email = inputEmail.text.toString()

            val result = viewModel.validateEmail(email = email)
            layoutEmail.helperText = result.emailError

            if (result.hasNoError) {
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        progressBar.show()
        viewModel.resetPassword(email).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> progressBar.hide()
                is Response.Failure -> {
                    progressBar.hide()
                    print(response.errorMessage)
                }
            }
        }
    }

}