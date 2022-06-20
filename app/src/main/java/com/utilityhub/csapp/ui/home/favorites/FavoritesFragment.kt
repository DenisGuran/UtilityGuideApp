package com.utilityhub.csapp.ui.home.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.databinding.FragmentFavoritesBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.profile.ProfileFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleUserState()
    }

    private fun handleUserState() {
        if (viewModel.isLoggedIn) {
            getFavorites()
            binding.apply {
                loggedInLayout.visibility = View.VISIBLE
                loggedOutLayout.visibility = View.GONE
            }
        } else {
            binding.btnLogin.setOnClickListener {
                navigateToAuth()
            }
        }
    }


    private fun navigateToAuth() {
        val navAuth = ProfileFragmentDirections.actionHomeToAuthentication()
        findNavController().navigate(navAuth)
    }

    private fun getFavorites() {
        viewModel.favorites.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    Log.i("Favorites", response.data.toString())
                }
                is Response.Failure -> {
                    Log.w("Error", response.errorMessage)
                }
            }
        }
    }

}