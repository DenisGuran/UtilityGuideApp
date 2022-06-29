package com.utilityhub.csapp.ui.auth.forgot_password

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.viewModels
import com.utilityhub.csapp.databinding.FragmentForgotPasswordBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.core.BaseFragment
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
        val context = requireContext()
        binding.apply {
            email = inputEmail.text.toString()

            val result = viewModel.validateEmail(email = email)
            layoutEmail.helperText = result.emailError?.asString(context)

            if (result.hasNoError) {
                resetPassword()
            }
        }
    }

    private fun resetPassword() {
        progressBar.show()
        viewModel.resetPassword(email).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "A password reset link has been sent to $email.",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar.hide()
                }
                is Response.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "No registered account found with this email address.",
                        Toast.LENGTH_LONG
                    ).show()
                    progressBar.hide()
                }
            }
        }
    }

}