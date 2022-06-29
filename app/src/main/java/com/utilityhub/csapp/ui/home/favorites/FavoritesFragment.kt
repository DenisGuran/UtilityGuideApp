package com.utilityhub.csapp.ui.home.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.databinding.FragmentFavoritesBinding
import com.utilityhub.csapp.domain.model.Favorite
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.ui.adapters.FavoriteAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.profile.ProfileFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate),
    FavoriteAdapter.OnFavoriteClickListener {

    private val viewModel by viewModels<FavoritesViewModel>()
    private var adapter = FavoriteAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isLoggedIn) {
            getFavorites()
            setAdapter()
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

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToAuth() {
        val navAuth = ProfileFragmentDirections.actionHomeToAuthentication()
        findNavController().navigate(navAuth)
    }

    private fun navigateToMaps() {
        val navMaps = FavoritesFragmentDirections.actionGlobalMapsFragment()
        findNavController().navigate(navMaps)
    }

    private fun getFavorites() {
        viewModel.favorites.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    val favorites = response.data!!
                    if (favorites.isNullOrEmpty()) {
                        binding.emptyFavLayout.visibility = View.VISIBLE
                        binding.btnBrowseTutorials.setOnClickListener {
                            navigateToMaps()
                        }
                    } else if(binding.emptyFavLayout.visibility == View.VISIBLE){
                        binding.emptyFavLayout.visibility = View.GONE
                    }

                    adapter.submitList(favorites)
                }
                is Response.Failure ->
                    Log.w("Error", response.errorMessage)
            }
        }
    }

    override fun onFavoriteClick(favorite: Favorite) {
        viewModel.getTutorial(
            map = favorite.map!!,
            utilityType = favorite.utilityType!!,
            landingSpot = favorite.landId!!,
            throwingSpot = favorite.throwId!!
        ).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    val navTutorial =
                        FavoritesFragmentDirections.actionFavoritesFragmentToTutorialFragment(
                            map = favorite.map!!,
                            utilityType = favorite.utilityType!!,
                            landingSpot = favorite.landing!!,
                            landId = favorite.landId!!,
                            throwingSpot = response.data
                        )
                    findNavController().navigate(navTutorial)
                }
                is Response.Failure ->
                    Log.w("Error", response.errorMessage)

            }
        }
    }

    override fun onRemoveFavoriteClick(favorite: Favorite) {
        viewModel.removeTutorialFromFavorites(favorite)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success ->
                        Toast.makeText(
                            requireContext(),
                            "Successfully removed from Favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    is Response.Failure -> Log.w("removeFromFavorites", response.errorMessage)
                }
            }
    }

}