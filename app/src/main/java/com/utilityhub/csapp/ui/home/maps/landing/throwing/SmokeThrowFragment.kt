package com.utilityhub.csapp.ui.home.maps.landing.throwing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
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
    private var throwingSpots = ArrayList<Utility>()
    private var adapter = UtilityAdapter(this)

    private val viewModel by viewModels<ThrowViewModel>()
    private val args: SmokeThrowFragmentArgs by navArgs()

    private lateinit var map: String
    private lateinit var landingSpot: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        getThrowingSpots()
        setAdapter()

        binding.btnMaps.setOnClickListener {
            navigateToMaps()
        }

    }

    private fun getThrowingSpots() {
        viewModel.getThrowingSpots(
            map = map,
            utility = Constants.SMOKES_REF,
            landingSpot = landingSpot
        ).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    if (throwSpots.isNotEmpty()) {
                        throwSpots.clear()
                    }
                    throwSpots.addAll(response.data)
                    if (throwingSpots.isNotEmpty()) {
                        throwingSpots.clear()
                    }
                    throwingSpots.addAll(throwSpots.map {
                        Utility(
                            name = it.name,
                            img = it.img
                        )
                    } as ArrayList<Utility>)

                    adapter.submitList(throwingSpots)
                }
                is Response.Failure -> Log.w("getThrowingSpots", response.errorMessage)
            }
        }
    }

    private fun getNavArgs() {
        landingSpot = args.landingSpot
        map = args.map
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToMaps() {
        findNavController().setGraph(R.navigation.nav_graph)
        val navHome = MapsFragmentDirections.actionGlobalHome()
        findNavController().navigate(navHome)
    }

    override fun onUtilityClick(position: Int) {
        val throwingSpot = throwSpots[position]
        val navTutorial =
            SmokeThrowFragmentDirections.actionSmokeThrowFragmentToTutorialFragment(
                landingSpot = landingSpot,
                throwingSpot = throwingSpot,
                map = map
            )
        findNavController().navigate(navTutorial)
    }

//    private fun initData() {
//
//        val throwPosLayout = binding.throwPosLayout
//        throwSpots.clear()
//
//        if (maps["mirage"] == true) {
//
//            throwPosLayout.setBackgroundResource(R.drawable.mirage_background_blur)
//
//            when (selectedSmoke) {
//                0 -> {
//                    throwSpots.add(
//                        Utility(
//                            "T Stairs",
//                            R.drawable.stairs_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "T Spawn",
//                            R.drawable.stairs_spawn1
//                        )
//                    )
//                }
//
//                1 -> {
//                    throwSpots.add(
//                        Utility(
//                            "T Roof",
//                            R.drawable.abench_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Underground",
//                            R.drawable.abench_spawn1
//                        )
//                    )
//                }
//            }
//        }
//
//        if (maps["inferno"] == true) {
//
//            throwPosLayout.setBackgroundResource(R.drawable.inferno_background_blur)
//
//            when (selectedSmoke) {
//                0 -> {
//                    throwSpots.add(
//                        Utility(
//                            "Banana",
//                            R.drawable.in_ct_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Barrels",
//                            R.drawable.in_barrels
//                        )
//                    )
//                }
//
//                1 -> {
//                    throwSpots.add(
//                        Utility(
//                            "Banana",
//                            R.drawable.in_coffins_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Banana Fast Land",
//                            R.drawable.in_coffins_spawn1
//                        )
//                    )
//                }
//
//                2 -> {
//                    throwSpots.add(
//                        Utility(
//                            "Mid",
//                            R.drawable.moto_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Underpass",
//                            R.drawable.moto_spawn1
//                        )
//                    )
//                }
//                3 -> {
//                    throwSpots.add(
//                        Utility(
//                            "Coffins",
//                            R.drawable.in_coffins
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Ruins entrance",
//                            R.drawable.in_tree_bench
//                        )
//                    )
//                }
//
//                4 -> {
//                    throwSpots.add(
//                        Utility(
//                            "Coffins",
//                            R.drawable.in_coffins
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Second box",
//                            R.drawable.in_2nd_box
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Triple",
//                            R.drawable.in_triple
//                        )
//                    )
//                    throwSpots.add(
//                        Utility(
//                            "Construction",
//                            R.drawable.in_construction
//                        )
//                    )
//                }
//            }
//
//        }
//
//    }

}