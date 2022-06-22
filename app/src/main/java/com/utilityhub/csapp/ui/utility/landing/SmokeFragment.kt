package com.utilityhub.csapp.ui.utility.landing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.databinding.FragmentSmokeBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.core.MainActivity
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmokeFragment :
    BaseFragment<FragmentSmokeBinding>(FragmentSmokeBinding::inflate),
    UtilityAdapter.OnUtilityClickListener {

    private val viewModel by hiltNavGraphViewModels<LandViewModel>(R.id.nav_utility)
    private val args: SmokeFragmentArgs by navArgs()

    private lateinit var map: String
    private var utilityType = Constants.SMOKES_REF
    private var utilityFilters = arrayListOf<String>()
    private var searchText = ""

    private var landingSpots = ArrayList<Utility>()
    private var adapter = UtilityAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        getLandingSpots()
        setAdapter()
        onBackPressedGoToMaps()
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
        binding.apply {
            searchView.apply {
                setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        clearFocus()
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        searchText = newText!!

                        if (searchText.isNotBlank()) {
                            utilityFilters.add(searchText)
                            filterByTagsAndText()
                            utilityFilters.remove(searchText)
                        } else {
                            adapter.filter.filter("")
                            filterByTagsAndText()
                        }

                        return true
                    }
                })
            }
            chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                if (utilityFilters.isNotEmpty())
                    utilityFilters.clear()
                checkedIds.forEach {
                    when (it) {
                        chipASite.id -> utilityFilters.add("A")
                        chipBSite.id -> utilityFilters.add("B")
                        chipOneWay.id -> utilityFilters.add("OW")
                        chipRetake.id -> utilityFilters.add("RT")
                    }
                }
                if (searchText.isNotBlank())
                    utilityFilters.add(searchText)
                adapter.filter.filter("")
                filterByTagsAndText()
                utilityFilters.remove(searchText)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
    }

    private fun filterByTagsAndText() {
        utilityFilters.forEach {
            adapter.filter.filter(it)
        }
    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
        if (bottomNav.menu.getItem(0).itemId == R.id.mapsFragment) {
            bottomNav.menu.clear()
            bottomNav.inflateMenu(R.menu.utility_menu)
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
        viewModel.setCurrentMap(args.map)
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
            SmokeFragmentDirections.actionSmokeFragmentToThrowFragment(
                map = map,
                utilityType = utilityType,
                landingSpot = landingSpot
            )
        findNavController().navigate(navThrow)
    }

}