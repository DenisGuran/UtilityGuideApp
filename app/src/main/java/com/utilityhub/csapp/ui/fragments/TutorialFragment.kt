package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utilityhub.csapp.R
import com.utilityhub.csapp.databinding.FragmentTutorialBinding
import com.utilityhub.csapp.domain.model.Tutorial
import com.utilityhub.csapp.domain.model.UtilityThrow
import com.utilityhub.csapp.ui.adapters.TutorialAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialFragment : BaseFragment<FragmentTutorialBinding>(FragmentTutorialBinding::inflate) {

    private val args: TutorialFragmentArgs by navArgs()
    private lateinit var map: String
    private lateinit var landingSpot: String
    private lateinit var throwingSpot: UtilityThrow
    private lateinit var tutorial: ArrayList<Tutorial>
    private var adapter = TutorialAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNavArgs()
        setAdapter()
        setTutorialName()

        binding.btnMaps.setOnClickListener {
            navigateToMaps()
        }

    }

    private fun getNavArgs(){
        landingSpot = args.landingSpot
        throwingSpot = args.throwingSpot
        tutorial = throwingSpot.tutorial as ArrayList<Tutorial>
        adapter.submitList(tutorial)
        map = args.map
    }

    private fun setTutorialName(){
        binding.textview1.text = throwingSpot.name.plus(" to ").plus(landingSpot)
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToMaps() {
        findNavController().setGraph(R.navigation.nav_graph)
        val navHome = MapsFragmentDirections.actionGlobalHome()
        findNavController().navigate(navHome)
    }

//    private fun initData() {
//
//        val tutorialLayout = binding.tutorialLayout
//
//        tutorialList.clear()
//        if (Global.maps["mirage"] == true) {
//
//            tutorialLayout.setBackgroundResource(R.drawable.mirage_background_blur)
//
//            when (selectedSmoke) {
//
//                0 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.stairs_pos0,
//                                "Position yourself here"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.stairs_lineup0,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.stairs_land0,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.stairs_pos1,
//                                "Position yourself in this corner"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.stairs_lineup1,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.stairs_land1,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//
//
//                1 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.abench_pos0,
//                                "Position yourself here"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.abench_lineup0,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.abench_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.abench_pos1,
//                                "Walk against the wall until you get stuck in this corner"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.abench_lineup1,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.abench_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//            }
//        } else if (Global.maps["inferno"] == true) {
//
//            tutorialLayout.setBackgroundResource(R.drawable.inferno_background_blur)
//
//            when (selectedSmoke) {
//
//                0 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_ct_pos0,
//                                "Position yourself in front of the doorbell"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_ct_lineup0,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_ct_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_barrels_ct_pos,
//                                "Get stuck between the barrel and the wooden plank"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_barrels_ct_lineup,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_barrels_ct_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//
//                1 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffins_pos0,
//                                "Position yourself in the front of the doorbell"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffins_lineup0,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffins_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffins_pos1,
//                                "Line yourself up with the corner of this shadow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffins_lineup1,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffins_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//
//                2 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.moto_pos0,
//                                "Position yourself here"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.moto_lineup0,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.moto_smoke,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.moto_pos1,
//                                "Position yourself over here"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.moto_lineup1,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.moto_smoke,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//
//                3 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffin_banana_pos,
//                                "Position yourself like this"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffin_banana_lineup,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_deep_banana_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_trash_banana_pos,
//                                "Get stuck between pillar and trash"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_trash_banana_lineup,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_deep_banana_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//
//                4 -> when (selectedPos) {
//                    0 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffin_pool_pos,
//                                "Position yourself here"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_coffin_pool_lineup,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_pool_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    1 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_2ndbox_pool_pos,
//                                "Position yourself like this"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_2ndbox_pool_lineup,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_pool_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    2 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_triple_pool_pos,
//                                "Position yourself in this zone"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_triple_pool,
//                                "Jumpthrow"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_pool_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//
//                    3 -> {
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_construction_pos,
//                                "Position yourself here"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_construction_pool,
//                                "Left click throw"
//                            )
//                        )
//                        tutorialList.add(
//                            Tutorial(
//                                tutorialList.size + 1,
//                                R.drawable.in_construction_land,
//                                "Landed smoke"
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }
}