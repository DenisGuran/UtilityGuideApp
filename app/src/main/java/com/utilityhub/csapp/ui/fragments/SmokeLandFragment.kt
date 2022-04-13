package com.utilityhub.csapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.utilityhub.csapp.R
import com.utilityhub.csapp.core.Global
import com.utilityhub.csapp.core.Global.Companion.maps
import com.utilityhub.csapp.databinding.FragmentSmokeLandBinding
import com.utilityhub.csapp.domain.model.Utility
import com.utilityhub.csapp.ui.activities.MainActivity
import com.utilityhub.csapp.ui.adapters.UtilityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SmokeLandFragment : BaseFragment<FragmentSmokeLandBinding>(FragmentSmokeLandBinding::inflate) {

    private lateinit var database: FirebaseFirestore
    private lateinit var landingSpotsRef: CollectionReference

    private var landingSpots = ArrayList<Utility>()
    private var adapter: UtilityAdapter? = null
    private var firestoreListener: ListenerRegistration? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        database = FirebaseFirestore.getInstance()
//        landingSpotsRef = database
//            .collection(Constants.MAPS)
//            .document(Constants.MIRAGE)
//            .collection(Constants.SMOKES)
        initData()
        setRecyclerView()
//        eventChangeListener()
        onBackPressedGoToMaps()
    }

    override fun onStart() {
        super.onStart()
        setUpBottomNavBar()
    }

    override fun onPause() {
        super.onPause()
        firestoreListener?.remove()
    }

//    private fun eventChangeListener() {
//        firestoreListener = landingSpotsRef.addSnapshotListener { value, _ ->
//            for (doc in value?.documentChanges!!) {
//                if(doc.type == DocumentChange.Type.ADDED){
//                    val landingSpot = doc.document.toObject(Data::class.java)
//                    landingSpots.add(landingSpot)
//                }
//            }
//            adapter?.notifyDataSetChanged()
//        }
//    }

    @OptIn(InternalCoroutinesApi::class)
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
            })
    }

    private fun setRecyclerView() {
        adapter =
            UtilityAdapter(landingSpots, object : UtilityAdapter.OnClickListener {
                override fun onItemClick(position: Int) {
                    val landingSpot = landingSpots[position].name
                    val navTutorial =
                        SmokeLandFragmentDirections.actionSmokeLandFragmentToSmokeThrowFragment(
                            landingSpot
                        )
                    Global.selectedSmoke = position
                    findNavController().navigate(navTutorial)
                }
            })
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this@SmokeLandFragment.activity)
            recyclerView.adapter = adapter
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
                Utility(
                    "CT Spawn",
                    R.drawable.in_ct_smoke
                )
            )
            landingSpots.add(
                Utility(
                    "Coffins",
                    R.drawable.in_coffins_land
                )
            )
            landingSpots.add(
                Utility(
                    "Moto",
                    R.drawable.moto_smoke
                )
            )
            landingSpots.add(
                Utility(
                    "Deep banana",
                    R.drawable.in_deep_banana_land
                )
            )
            landingSpots.add(
                Utility(
                    "Pool",
                    R.drawable.in_pool_land
                )
            )
        }

        if (maps["dust2"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.dust2_background_blur)
            landingSpots.add(
                Utility(
                    "Coming Soon",
                    R.drawable.dust2_background
                )
            )
        }

        if (maps["overpass"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.overpass_background_blur)
            landingSpots.add(
                Utility(
                    "Coming Soon",
                    R.drawable.overpass_background
                )
            )
        }

        if (maps["nuke"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.nuke_background_blur)
            landingSpots.add(
                Utility(
                    "Coming Soon",
                    R.drawable.nuke_background
                )
            )
        }

        if (maps["vertigo"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
            landingSpots.add(
                Utility(
                    "Coming Soon",
                    R.drawable.vertigo_background
                )
            )
        }

        if (maps["ancient"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.ancient_background_blur)
            landingSpots.add(
                Utility(
                    "Coming Soon",
                    R.drawable.ancient_background
                )
            )
        }
    }

}