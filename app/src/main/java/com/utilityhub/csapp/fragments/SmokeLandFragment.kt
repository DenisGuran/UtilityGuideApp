package com.utilityhub.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.utilityhub.csapp.Global.Companion.land
import com.utilityhub.csapp.Global.Companion.maps
import com.utilityhub.csapp.Global.Companion.selectedSmoke
import com.utilityhub.csapp.R
import com.utilityhub.csapp.activities.AuthenticationActivity
import com.utilityhub.csapp.adapters.UtilityAdapter
import com.utilityhub.csapp.databinding.FragmentSmokeLandBinding
import com.utilityhub.csapp.models.Data
import com.google.firebase.firestore.FirebaseFirestore

class SmokeLandFragment : Fragment(R.layout.fragment_smoke_land) {

    private val landingSpots = ArrayList<Data>()
    private lateinit var database: FirebaseFirestore

    private var _binding: FragmentSmokeLandBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmokeLandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseFirestore.getInstance()

        initData()
        setRecyclerView()
        onBackPressedGoToMaps()
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUpBottomNavBar() {
        val bottomNav = (requireActivity() as AuthenticationActivity).bottomNav
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
            })
    }

    private fun setRecyclerView() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(this@SmokeLandFragment.activity)
            recyclerView.adapter =
                UtilityAdapter(landingSpots, object : UtilityAdapter.OnClickListener {
                    override fun onItemClick(position: Int) {
                        val navThrow =
                            SmokeLandFragmentDirections.actionSmokeLandFragmentToSmokeThrowFragment()
                        findNavController().navigate(navThrow)
                        selectedSmoke = position
                        land = landingSpots[position].name
                    }
                })
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun initData() {

        val smokeLayout = binding.smokeLayout
        landingSpots.clear()

        if (maps["mirage"] == true) {

            smokeLayout.setBackgroundResource(R.drawable.mirage_background_blur)
        }

        if (maps["inferno"] == true) {

            smokeLayout.setBackgroundResource(R.drawable.inferno_background_blur)

            landingSpots.add(
                Data(
                    "CT Spawn",
                    R.drawable.in_ct_smoke
                )
            )
            landingSpots.add(
                Data(
                    "Coffins",
                    R.drawable.in_coffins_land
                )
            )
            landingSpots.add(
                Data(
                    "Moto",
                    R.drawable.moto_smoke
                )
            )
            landingSpots.add(
                Data(
                    "Deep banana",
                    R.drawable.in_deep_banana_land
                )
            )
            landingSpots.add(
                Data(
                    "Pool",
                    R.drawable.in_pool_land
                )
            )
        }

        if (maps["dust2"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.dust2_background_blur)
            landingSpots.add(
                Data(
                    "Coming Soon",
                    R.drawable.dust2_background
                )
            )
        }

        if (maps["overpass"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.overpass_background_blur)
            landingSpots.add(
                Data(
                    "Coming Soon",
                    R.drawable.overpass_background
                )
            )
        }

        if (maps["nuke"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.nuke_background_blur)
            landingSpots.add(
                Data(
                    "Coming Soon",
                    R.drawable.nuke_background
                )
            )
        }

        if (maps["vertigo"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
            landingSpots.add(
                Data(
                    "Coming Soon",
                    R.drawable.vertigo_background
                )
            )
        }

        if (maps["ancient"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.ancient_background_blur)
            landingSpots.add(
                Data(
                    "Coming Soon",
                    R.drawable.ancient_background
                )
            )
        }
    }

}