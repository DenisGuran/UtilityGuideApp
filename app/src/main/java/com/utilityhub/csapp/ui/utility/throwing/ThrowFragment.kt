package com.utilityhub.csapp.ui.utility.throwing

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utilityhub.csapp.R
import com.utilityhub.csapp.common.Constants
import com.utilityhub.csapp.common.Utils
import com.utilityhub.csapp.databinding.FragmentThrowBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.domain.model.UtilityThrow
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThrowFragment :
    BaseFragment<FragmentThrowBinding>(FragmentThrowBinding::inflate),
    UtilityAdapter.OnUtilityClickListener {

    private var throwSpots = ArrayList<UtilityThrow>()
    private var throwSpotsWithoutTutorial = ArrayList<Utility>()
    private var throwSpotsFiltered = arrayListOf<Utility>()
    private var adapter = UtilityAdapter(this)

    private val viewModel by viewModels<ThrowViewModel>()
    private val args: ThrowFragmentArgs by navArgs()

    private lateinit var map: String
    private lateinit var landingSpot: String
    private lateinit var landId: String
    private lateinit var utilityType: String
    private var isTickRateTouched = false
    private var utilityFilters = arrayListOf("")
    private var searchText = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        setBackground()
        getPreferences()
        setAdapter()

        binding.apply {
            landSpot.text = landingSpot

            btnMaps.setOnClickListener {
                navigateToMaps()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStart() {
        super.onStart()
        binding.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
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
            switchTickrate.apply {
                setOnTouchListener { _, _ ->
                    isTickRateTouched = true
                    false
                }
                setOnCheckedChangeListener { _, isChecked ->
                    if (isTickRateTouched) {
                        if (utilityFilters.isNotEmpty())
                            utilityFilters.clear()
                        isTickRateTouched = false
                        val tickrate = if (isChecked) {
                            textOn.toString()
                        } else {
                            textOff.toString()
                        }
                        utilityFilters.add(tickrate)
                        if (searchText.isNotBlank())
                            utilityFilters.add(searchText)
                        adapter.filter.filter("")
                        filterByTagsAndText()
                        utilityFilters.remove(searchText)
                    }
                }
            }
        }
    }

    private fun getPreferences() {
        viewModel.getPreferences().observe(viewLifecycleOwner) {
            viewModel.setDefaultTickrate(it.tickrate)
            getThrowingSpots()
            binding.switchTickrate.apply {
                isChecked = it.tickrate != Constants.TAG_128
                requestFocus()
                if(utilityFilters.isNullOrEmpty())
                utilityFilters.add(it.tickrate)
            }
        }
    }

    private fun filterByTagsAndText() {
        println(utilityFilters.toString())
        utilityFilters.forEach {
            adapter.filter.filter(it)
        }
    }

    private fun getThrowingSpots() {
        viewModel.getThrowingSpots(
            map = map,
            utilityType = utilityType,
            landingSpot = landId
        ).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    if (throwSpots.isNotEmpty()) {
                        throwSpots.clear()
                    }
                    throwSpots.addAll(response.data)
                    if (throwSpotsWithoutTutorial.isNotEmpty()) {
                        throwSpotsWithoutTutorial.clear()
                    }
                    throwSpotsWithoutTutorial.addAll(throwSpots.map {
                        Utility(
                            id = it.id,
                            name = it.name,
                            img = it.img,
                            tags = it.tags
                        )
                    } as ArrayList<Utility>)

                    throwSpotsWithoutTutorial.forEach {
                        if(it.tags?.contains(viewModel.defaultTickrate.value) == true)
                            throwSpotsFiltered.add(it)
                    }

                    adapter.setMainList(throwSpotsWithoutTutorial)
                    adapter.submitList(throwSpotsFiltered)
                }
                is Response.Failure -> Log.w("getThrowingSpots", response.errorMessage)
            }
        }
    }

    private fun setBackground() {
        val backgroundBlur = Utils.getMapBackgroundBlurDrawable(map, requireContext())
        binding.throwLayout.background = backgroundBlur
    }

    private fun getNavArgs() {
        landingSpot = args.landingSpot
        landId = args.landId
        map = args.map
        utilityType = args.utilityType
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToMaps() {
        findNavController().setGraph(R.navigation.nav_graph)
        val navHome = MapsFragmentDirections.actionGlobalHome()
        findNavController().navigate(navHome)
    }

    override fun onUtilityClick(utility: Utility) {
        val throwingSpot = throwSpots[throwSpotsWithoutTutorial.indexOf(utility)]
        val navTutorial =
            ThrowFragmentDirections.actionThrowFragmentToTutorialFragment(
                map = map,
                utilityType = utilityType,
                landingSpot = landingSpot,
                landId = landId,
                throwingSpot = throwingSpot
            )
        findNavController().navigate(navTutorial)
    }

}