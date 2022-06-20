package com.utilityhub.csapp.ui.utility.landing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.FragmentFlashBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlashFragment : BaseFragment<FragmentFlashBinding>(FragmentFlashBinding::inflate),
    UtilityAdapter.OnUtilityClickListener {

    private val viewModel by hiltNavGraphViewModels<LandViewModel>(R.id.nav_utility)

    private val utilityType = "Flashes"
    private lateinit var map : String

    private var landingSpots = ArrayList<Utility>()
    private var adapter = UtilityAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentMap.observe(viewLifecycleOwner) {
            map = it
            getLandingSpots()
        }
        onBackPressedGoToMaps()
    }

    private fun getLandingSpots() {
        viewModel.getLandingSpots(map = map, utilityType = utilityType)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success -> {
                        if (landingSpots.isNotEmpty()) {
                            landingSpots.clear()
                        }
                        landingSpots.addAll(response.data)
                        Log.i("FLASH LAND", landingSpots.toString())
                        Log.i("MAP", map)
                        Log.i("utility", utilityType)

                    }
                    is Response.Failure -> Log.w("getLandingSpots", response.errorMessage)
                }
            }
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

    override fun onUtilityClick(utility: Utility) {
        TODO("Not yet implemented")
    }

}