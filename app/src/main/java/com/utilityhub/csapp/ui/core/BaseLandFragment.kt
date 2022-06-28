package com.utilityhub.csapp.ui.core

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.utilityhub.csapp.R
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import com.utilityhub.csapp.ui.utility.landing.LandViewModel


@Suppress("LeakingThis")
abstract class BaseLandFragment<VB : ViewBinding>(
    inflate: Inflate<VB>
) : BaseFragment<VB>(inflate), UtilityAdapter.OnUtilityClickListener{

    val viewModel by hiltNavGraphViewModels<LandViewModel>(R.id.nav_utility)
    var adapter = UtilityAdapter(onUtilityClickListener = this)

    var utilityFilters = arrayListOf<String>()
    var searchText = ""
    var landingSpots = ArrayList<Utility>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
        )
    }
}