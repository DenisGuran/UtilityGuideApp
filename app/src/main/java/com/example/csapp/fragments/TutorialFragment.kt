package com.example.csapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csapp.Global
import com.example.csapp.Global.Companion.land
import com.example.csapp.Global.Companion.pos
import com.example.csapp.Global.Companion.selectedPos
import com.example.csapp.Global.Companion.selectedSmoke
import com.example.csapp.activities.MainActivity
import com.example.csapp.R
import com.example.csapp.adapters.TutorialAdapter
import com.example.csapp.databinding.FragmentTutorialBinding
import com.example.csapp.models.TutorialData

class TutorialFragment : Fragment(R.layout.fragment_tutorial) {

    private val tutorialList = ArrayList<TutorialData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTutorialBinding.bind(view)

        binding.apply {
            initData(this)
            setRecyclerView(this)
            textview1.text = pos.plus(" to ").plus(land)
            btnMaps.setOnClickListener {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun setRecyclerView(binding: FragmentTutorialBinding) {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@TutorialFragment.activity)
            recyclerView.adapter = context?.let { TutorialAdapter(it,tutorialList) }
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun initData(binding: FragmentTutorialBinding) {

        val tutorialLayout = binding.tutorialLayout

        tutorialList.clear()

        if (Global.maps["mirage"] == true) {

            tutorialLayout.setBackgroundResource(R.drawable.mirage_background_blur)

            when (selectedSmoke) {

                0 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.stairs_pos0,
                                "Position yourself here"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.stairs_lineup0,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.stairs_land0,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.stairs_pos1,
                                "Position yourself in this corner"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.stairs_lineup1,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.stairs_land1,
                                "Landed smoke"
                            )
                        )
                    }
                }


                1 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.abench_pos0,
                                "Position yourself here"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.abench_lineup0,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.abench_land,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.abench_pos1,
                                "Walk against the wall until you get stuck in this corner"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.abench_lineup1,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.abench_land,
                                "Landed smoke"
                            )
                        )
                    }
                }
            }
        }

        else if (Global.maps["inferno"] == true) {

            tutorialLayout.setBackgroundResource(R.drawable.inferno_background_blur)

            when (selectedSmoke) {

                0 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_ct_pos0,
                                "Position yourself in front of the doorbell"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_ct_lineup0,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_ct_land,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_barrels_ct_pos,
                                "Get stuck between the barrel and the wooden plank"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_barrels_ct_lineup,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_barrels_ct_land,
                                "Landed smoke"
                            )
                        )
                    }
                }

                1 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_coffins_pos0,
                                "Position yourself in the front of the doorbell"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_coffins_lineup0,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_coffins_land,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_coffins_pos1,
                                "Line yourself up with the corner of this shadow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(
                                tutorialList.size + 1,
                                R.drawable.in_coffins_lineup1,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_coffins_land,
                                "Landed smoke"
                            )
                        )
                    }
                }

                2 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.moto_pos0,
                                "Position yourself here"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.moto_lineup0,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.moto_smoke,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.moto_pos1,
                                "Position yourself over here"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.moto_lineup1,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.moto_smoke,
                                "Landed smoke"
                            )
                        )
                    }
                }

                3 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_coffin_banana_pos,
                                "Position yourself like this"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_coffin_banana_lineup,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_deep_banana_land,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_trash_banana_pos,
                                "Get stuck between pillar and trash"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_trash_banana_lineup,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_deep_banana_land,
                                "Landed smoke"
                            )
                        )
                    }
                }

                4 -> when (selectedPos) {
                    0 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_coffin_pool_pos,
                                "Position yourself here"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_coffin_pool_lineup,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_pool_land,
                                "Landed smoke"
                            )
                        )
                    }

                    1 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_2ndbox_pool_pos,
                                "Position yourself like this"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_2ndbox_pool_lineup,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_pool_land,
                                "Landed smoke"
                            )
                        )
                    }

                    2 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_triple_pool_pos,
                                "Position yourself in this zone"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_triple_pool,
                                "Jumpthrow"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_pool_land,
                                "Landed smoke"
                            )
                        )
                    }

                    3 -> {
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_construction_pos,
                                "Position yourself here"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_construction_pool,
                                "Left click throw"
                            )
                        )
                        tutorialList.add(
                            TutorialData(tutorialList.size + 1,
                                R.drawable.in_construction_land,
                                "Landed smoke"
                            )
                        )
                    }
                }
            }
        }
    }
}