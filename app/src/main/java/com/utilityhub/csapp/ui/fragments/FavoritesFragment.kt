package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Global
import com.utilityhub.csapp.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        onBackPressedGoToMaps()
    }

    private fun onBackPressedGoToMaps() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                    findNavController().setGraph(R.navigation.nav_graph)
                    val navHome = MapsFragmentDirections.actionGlobalHome()
                    findNavController().navigate(navHome)
                }
            })
    }

    private fun initData() {

        val favoritesLayout = binding.favoritesLayout

        if (Global.maps["mirage"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.mirage_background_blur)
        }

        if (Global.maps["inferno"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.inferno_background_blur)
        }

        if (Global.maps["dust2"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.dust2_background_blur)
        }

        if (Global.maps["overpass"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.overpass_background_blur)
        }

        if (Global.maps["nuke"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.nuke_background_blur)
        }

        if (Global.maps["vertigo"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
        }

        if (Global.maps["ancient"] == true) {
            favoritesLayout.setBackgroundResource(R.drawable.ancient_background_blur)
        }

    }

}