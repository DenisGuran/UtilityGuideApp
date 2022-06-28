package com.utilityhub.csapp.ui.home.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.data.local.Preferences
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.databinding.FragmentProfileBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.core.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    private var defaultTickrate = Constants.TAG_128
    private var isTickRateTouched = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUserState()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleUserState() {
        if (viewModel.isLoggedIn) {
            getUserProfile()
            getPreferences()
            binding.apply {
                loggedInLayout.visibility = View.VISIBLE
                loggedOutLayout.visibility = View.GONE
                btnLogout.setOnClickListener {
                    signOut()
                }
                switchTickrate.apply {
                    setOnTouchListener { _, _ ->
                        isTickRateTouched = true
                        false
                    }
                    setOnCheckedChangeListener { _, isChecked ->
                        if (isTickRateTouched) {
                            isTickRateTouched = false
                            defaultTickrate = if (isChecked) {
                                textOn.toString()
                            } else {
                                textOff.toString()
                            }
                            viewModel.savePreferences(Preferences(tickrate = defaultTickrate))
                        }
                    }
                }
            }
        } else {
            binding.btnLogin.setOnClickListener {
                navigateToAuth()
            }
        }
    }

    private fun getPreferences() {
        viewModel.getPreferences().observe(viewLifecycleOwner) {
            if (it.tickrate != defaultTickrate) {
                binding.switchTickrate.apply {
                    isChecked = true
                    requestFocus()
                }
            }
        }
    }


    private fun navigateToAuth() {
        val navAuth = ProfileFragmentDirections.actionHomeToAuthentication()
        findNavController().navigate(navAuth)
    }

    private fun signOut() {
        viewModel.signOut().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> navigateToAuth()
                is Response.Failure -> print(response.errorMessage)
            }
        }
    }

    private fun getUserProfile() {
        viewModel.userProfile.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    val currentUser = response.data
                    binding.username.text =
                        if (currentUser.username!!.endsWith("s"))
                            "${currentUser.username}'"
                        else
                            "${currentUser.username}'s"
                }
                is Response.Failure -> {
                    print(response.errorMessage)
                }
            }
        }
    }
}