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
import com.utilityhub.csapp.databinding.FragmentSmokeThrowBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.domain.model.UtilityThrow
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmokeThrowFragment :
    BaseFragment<FragmentSmokeThrowBinding>(FragmentSmokeThrowBinding::inflate),
    UtilityAdapter.OnUtilityClickListener {

    private var throwSpots = ArrayList<UtilityThrow>()
    private var throwSpotsWithoutTutorial = ArrayList<Utility>()
    private var adapter = UtilityAdapter(this)

    private val viewModel by viewModels<ThrowViewModel>()
    private val args: SmokeThrowFragmentArgs by navArgs()

    private lateinit var map: String
    private lateinit var landingSpot: String
    private lateinit var utilityType: String
    private var isTickrateTouched = false
    private var tickrate = "128"

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        getThrowingSpots()
        setAdapter()

        binding.apply {
            btnMaps.setOnClickListener {
                navigateToMaps()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        adapter.filter.filter(it)
                    }
                    return true
                }
            })
            switchCompat.apply {
                setOnTouchListener { _, _ ->
                    isTickrateTouched = true
                    false
                }

                setOnCheckedChangeListener { _, isChecked ->
                    if (isTickrateTouched) {
                        isTickrateTouched = false
                        tickrate = if (isChecked) {
                            switchCompat.textOn.toString()
                        } else {
                            switchCompat.textOff.toString()
                        }
                    }
                }
            }
        }

    }

    private fun getThrowingSpots() {
        viewModel.getThrowingSpots(
            map = map,
            utilityType = utilityType,
            landingSpot = landingSpot
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
                            name = it.name,
                            img = it.img
                        )
                    } as ArrayList<Utility>)

                    adapter.setData(throwSpotsWithoutTutorial)
                }
                is Response.Failure -> Log.w("getThrowingSpots", response.errorMessage)
            }
        }
    }

    private fun getNavArgs() {
        landingSpot = args.landingSpot
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
            SmokeThrowFragmentDirections.actionSmokeThrowFragmentToTutorialFragment(
                map = map,
                utilityType = utilityType,
                landingSpot = landingSpot,
                throwingSpot = throwingSpot
            )
        findNavController().navigate(navTutorial)
    }

}