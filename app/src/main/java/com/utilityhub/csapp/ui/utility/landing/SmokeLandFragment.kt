package com.utilityhub.csapp.ui.utility.landing

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.databinding.FragmentSmokeLandBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.core.MainActivity
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmokeLandFragment :
    BaseFragment<FragmentSmokeLandBinding>(FragmentSmokeLandBinding::inflate),
    UtilityAdapter.OnUtilityClickListener {

    private val viewModel by viewModels<LandViewModel>()
    private val args: SmokeLandFragmentArgs by navArgs()

    private lateinit var map: String
    private var utilityType = Constants.SMOKES_REF

    private var landingSpots = ArrayList<Utility>()
    private var adapter = UtilityAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        getLandingSpots()
        setAdapter()
        onBackPressedGoToMaps()

        binding.apply {
            searchView.apply {
                setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            adapter.filter.filter(it)
                        }
                        return true
                    }
                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()

    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
        if (bottomNav.menu.getItem(0).itemId == R.id.mapsFragment) {
            bottomNav.menu.clear()
            bottomNav.inflateMenu(R.menu.utility_menu)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_utility_smoke -> {
                utilityType = Constants.SMOKES_REF
                true
            }
            R.id.flashFragment -> {
                utilityType = "Flashes"
                true
            }
            R.id.molotovFragment -> {
                utilityType = "Molotovs"
                true
            }
            R.id.retakeFragment -> {
                utilityType = "He grenades"
                true
            }
            else -> true
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
            }
        )
    }

    private fun getNavArgs() {
        map = args.map
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
                        adapter.setData(landingSpots)
                    }
                    is Response.Failure -> Log.w("getLandingSpots", response.errorMessage)
                }
            }
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    override fun onUtilityClick(utility: Utility) {
        val landingSpot = utility.name!!
        val navThrow =
            SmokeLandFragmentDirections.actionSmokeLandFragmentToSmokeThrowFragment(
                map = map,
                utilityType = utilityType,
                landingSpot = landingSpot
            )
        findNavController().navigate(navThrow)
    }

}