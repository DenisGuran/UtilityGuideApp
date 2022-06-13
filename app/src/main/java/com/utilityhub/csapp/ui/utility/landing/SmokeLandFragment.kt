package com.utilityhub.csapp.ui.utility.landing

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Constants
import com.utilityhub.csapp.databinding.FragmentSmokeLandBinding
import com.utilityhub.csapp.domain.model.Response
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.core.MainActivity
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import com.utilityhub.csapp.ui.core.BaseFragment
import com.utilityhub.csapp.ui.home.maps.MapsFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SmokeLandFragment :
    BaseFragment<FragmentSmokeLandBinding>(FragmentSmokeLandBinding::inflate),
    UtilityAdapter.OnUtilityClickListener {

    private val viewModel by viewModels<LandViewModel>()
    private val args: SmokeLandFragmentArgs by navArgs()

    private lateinit var map: String
    private var utility = Constants.SMOKES_REF

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

    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as MainActivity).bottomNav
        if (bottomNav.menu.getItem(0).itemId == R.id.mapsFragment) {
            bottomNav.menu.clear()
            bottomNav.inflateMenu(R.menu.utility_menu)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.nav_utility_smoke -> {
                utility = Constants.SMOKES_REF
                true
            }
            R.id.flashFragment -> {
                utility = "Flashes"
                true
            }
            R.id.molotovFragment -> {
                utility = "Molotovs"
                true
            }
            R.id.retakeFragment -> {
                utility = "He grenades"
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
        viewModel.getLandingSpots(map = map, utility = utility)
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Response.Success -> {
                        if (landingSpots.isNotEmpty()) {
                            landingSpots.clear()
                        }
                        landingSpots.addAll(response.data)
                        adapter.submitList(landingSpots)
                    }
                    is Response.Failure -> Log.w("getLandingSpots", response.errorMessage)
                }
            }
    }

    private fun setAdapter() {
        binding.recyclerView.adapter = adapter
    }

    override fun onUtilityClick(position: Int) {
        val landingSpot = landingSpots[position].name!!
        val navThrow =
            SmokeLandFragmentDirections.actionSmokeLandFragmentToSmokeThrowFragment(
                landingSpot = landingSpot,
                map = map
            )
        findNavController().navigate(navThrow)
    }

//    private fun initData() {
//
//        val smokeLayout = binding.smokeLayout
//        landingSpots.clear()
//
//        if (maps["mirage"] == true) {
//
//            smokeLayout.setBackgroundResource(R.drawable.mirage_background_blur)
//        }
//
//        if (maps["inferno"] == true) {
//
//            smokeLayout.setBackgroundResource(R.drawable.inferno_background_blur)
//
//            landingSpots.add(
//                Utility(
//                    "CT Spawn",
//                    R.drawable.in_ct_smoke
//                )
//            )
//            landingSpots.add(
//                Utility(
//                    "Coffins",
//                    R.drawable.in_coffins_land
//                )
//            )
//            landingSpots.add(
//                Utility(
//                    "Moto",
//                    R.drawable.moto_smoke
//                )
//            )
//            landingSpots.add(
//                Utility(
//                    "Deep banana",
//                    R.drawable.in_deep_banana_land
//                )
//            )
//            landingSpots.add(
//                Utility(
//                    "Pool",
//                    R.drawable.in_pool_land
//                )
//            )
//        }
//
//        if (maps["dust2"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.dust2_background_blur)
//            landingSpots.add(
//                Utility(
//                    "Coming Soon",
//                    R.drawable.dust2_background
//                )
//            )
//        }
//
//        if (maps["overpass"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.overpass_background_blur)
//            landingSpots.add(
//                Utility(
//                    "Coming Soon",
//                    R.drawable.overpass_background
//                )
//            )
//        }
//
//        if (maps["nuke"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.nuke_background_blur)
//            landingSpots.add(
//                Utility(
//                    "Coming Soon",
//                    R.drawable.nuke_background
//                )
//            )
//        }
//
//        if (maps["vertigo"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
//            landingSpots.add(
//                Utility(
//                    "Coming Soon",
//                    R.drawable.vertigo_background
//                )
//            )
//        }
//
//        if (maps["ancient"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.ancient_background_blur)
//            landingSpots.add(
//                Utility(
//                    "Coming Soon",
//                    R.drawable.ancient_background
//                )
//            )
//        }
//    }

}