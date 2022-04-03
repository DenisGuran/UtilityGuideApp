package com.utilityhub.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.utilityhub.csapp.Global
import com.utilityhub.csapp.R
import com.utilityhub.csapp.activities.AuthenticationActivity
import com.utilityhub.csapp.adapters.UtilityAdapter
import com.utilityhub.csapp.databinding.FragmentSmokeLandBinding
import com.utilityhub.csapp.models.Data
import com.utilityhub.csapp.utils.Constants

class SmokeLandFragment : Fragment(R.layout.fragment_smoke_land) {

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
        val query = FirebaseFirestore.getInstance()
            .collection(Constants.MAPS)
            .document(Constants.MIRAGE)
            .collection(Constants.SMOKES)
        val options = FirestoreRecyclerOptions.Builder<Data>()
            .setLifecycleOwner(this.viewLifecycleOwner)
            .setQuery(query, Data::class.java)
            .build()
        val landAdapter =
            UtilityAdapter(object : UtilityAdapter.OnClickListener {
                override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
                    val landingSpot = documentSnapshot.toObject(Data::class.java)?.name.toString()
                    val navTutorial =
                        SmokeLandFragmentDirections.actionSmokeLandFragmentToSmokeThrowFragment(
                            landingSpot
                        )
                    Global.selectedSmoke = position
                    findNavController().navigate(navTutorial)
                }
            }, options)
        binding.apply {
            recyclerView.adapter = landAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@SmokeLandFragment.activity)
            recyclerView.setHasFixedSize(true)
        }
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
//                Data(
//                    "CT Spawn",
//                    R.drawable.in_ct_smoke
//                )
//            )
//            landingSpots.add(
//                Data(
//                    "Coffins",
//                    R.drawable.in_coffins_land
//                )
//            )
//            landingSpots.add(
//                Data(
//                    "Moto",
//                    R.drawable.moto_smoke
//                )
//            )
//            landingSpots.add(
//                Data(
//                    "Deep banana",
//                    R.drawable.in_deep_banana_land
//                )
//            )
//            landingSpots.add(
//                Data(
//                    "Pool",
//                    R.drawable.in_pool_land
//                )
//            )
//        }
//
//        if (maps["dust2"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.dust2_background_blur)
//            landingSpots.add(
//                Data(
//                    "Coming Soon",
//                    R.drawable.dust2_background
//                )
//            )
//        }
//
//        if (maps["overpass"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.overpass_background_blur)
//            landingSpots.add(
//                Data(
//                    "Coming Soon",
//                    R.drawable.overpass_background
//                )
//            )
//        }
//
//        if (maps["nuke"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.nuke_background_blur)
//            landingSpots.add(
//                Data(
//                    "Coming Soon",
//                    R.drawable.nuke_background
//                )
//            )
//        }
//
//        if (maps["vertigo"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
//            landingSpots.add(
//                Data(
//                    "Coming Soon",
//                    R.drawable.vertigo_background
//                )
//            )
//        }
//
//        if (maps["ancient"] == true) {
//            smokeLayout.setBackgroundResource(R.drawable.ancient_background_blur)
//            landingSpots.add(
//                Data(
//                    "Coming Soon",
//                    R.drawable.ancient_background
//                )
//            )
//        }
//    }

}