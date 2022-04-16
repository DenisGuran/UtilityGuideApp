package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.databinding.FragmentProfileBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserProfile()

        binding.apply {
            btnLogout.setOnClickListener {
                signOut()
            }
        }
    }

    private fun navigateToAuth(){
        val navAuth = ProfileFragmentDirections.actionHomeToAuthentication()
        findNavController().navigate(navAuth)
    }

    private fun signOut() {
        viewModel.signOut().observe(viewLifecycleOwner){ response ->
            when(response){
                is Response.Success -> navigateToAuth()
                is Response.Failure -> print(response.errorMessage)
            }
        }
    }

    private fun getUserProfile() {
        viewModel.userProfile.observe(viewLifecycleOwner){ response ->
            when (response) {
                is Response.Success -> {
                    val currentUser = response.data
                    binding.apply {
                        email.text = currentUser.email
                        username.text = currentUser.username
                    }
                }
                is Response.Failure -> {
                    print(response.errorMessage)
                }
            }
        }
    }
}