package com.utilityhub.csapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.core.Global
import com.utilityhub.csapp.databinding.FragmentSmokeThrowBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.viewmodels.ThrowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SmokeThrowFragment : BaseFragment<FragmentSmokeThrowBinding>(FragmentSmokeThrowBinding::inflate) {

    private var throwSpots = ArrayList<Utility>()
    private var adapter: UtilityAdapter? = null
    private val viewModel by viewModels<ThrowViewModel>()

    private val args: SmokeThrowFragmentArgs by navArgs()
    private lateinit var landingSpot: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        landingSpot = args.landingSpot
        getThrowingSpots()
        setRecyclerView()
        binding.apply {
            btnMaps.setOnClickListener {
                findNavController().setGraph(R.navigation.nav_graph)
                val navHome = MapsFragmentDirections.actionGlobalHome()
                findNavController().navigate(navHome)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getThrowingSpots() {
        viewModel.getThrowingSpots(map = Constants.MIRAGE, utility = Constants.SMOKES, landingSpot = landingSpot).observe(viewLifecycleOwner){ response ->
            when(response){
                is Response.Success -> {
                    Log.i("LIST SUCCESS", response.data.toString())
                    if(throwSpots.isNotEmpty()){
                        throwSpots.clear()
                    }
                    throwSpots.addAll(response.data)
                    adapter?.notifyDataSetChanged()
                }
                is Response.Failure -> Log.w("LIST ERROR", response.errorMessage)
            }
        }
    }

    private fun setRecyclerView() {
        adapter =
            UtilityAdapter(throwSpots, object : UtilityAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    val throwingPos = throwSpots[position].name
                    val navTutorial =
                        SmokeThrowFragmentDirections.actionSmokeThrowFragmentToTutorialFragment(
                            landingSpot,
                            throwingPos!!
                        )
                    Global.selectedPos = position
                    findNavController().navigate(navTutorial)
                }
            })
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this@SmokeThrowFragment.context)
            recyclerView.setHasFixedSize(true)
        }
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