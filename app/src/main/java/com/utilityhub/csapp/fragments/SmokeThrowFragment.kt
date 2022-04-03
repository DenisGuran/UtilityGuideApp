package com.utilityhub.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.utilityhub.csapp.Global
import com.utilityhub.csapp.R
import com.utilityhub.csapp.adapters.UtilityAdapter
import com.utilityhub.csapp.databinding.FragmentSmokeThrowBinding
import com.utilityhub.csapp.models.Data
import com.utilityhub.csapp.utils.Constants


class SmokeThrowFragment : Fragment(R.layout.fragment_smoke_throw) {

    private var _binding: FragmentSmokeThrowBinding? = null
    private val binding get() = _binding!!

    private val args: SmokeThrowFragmentArgs by navArgs()
    private lateinit var landingSpot: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmokeThrowBinding.inflate(inflater, container, false)
        landingSpot = args.landingSpot
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initData()
        setRecyclerView()

        binding.apply {
            btnMaps.setOnClickListener {
                findNavController().setGraph(R.navigation.nav_graph)
                val navHome = MapsFragmentDirections.actionGlobalHome()
                findNavController().navigate(navHome)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setRecyclerView() {
        val query = FirebaseFirestore.getInstance()
            .collection(Constants.MAPS)
            .document(Constants.MIRAGE)
            .collection(Constants.SMOKES)
            .document(landingSpot)
            .collection(Constants.THROW)
        val options = FirestoreRecyclerOptions.Builder<Data>()
            .setLifecycleOwner(this.viewLifecycleOwner)
            .setQuery(query, Data::class.java)
            .build()
        val throwAdapter =
            UtilityAdapter(object : UtilityAdapter.OnClickListener {
                override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
                    val throwingPos = documentSnapshot.toObject(Data::class.java)?.name.toString()
                    val navTutorial =
                        SmokeThrowFragmentDirections.actionSmokeThrowFragmentToTutorialFragment(
                            landingSpot,
                            throwingPos
                        )
                    Global.selectedPos = position
                    findNavController().navigate(navTutorial)
                }
            }, options)
        binding.apply {
            recyclerView.adapter = throwAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@SmokeThrowFragment.activity)
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
//                        Data(
//                            "T Stairs",
//                            R.drawable.stairs_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "T Spawn",
//                            R.drawable.stairs_spawn1
//                        )
//                    )
//                }
//
//                1 -> {
//                    throwSpots.add(
//                        Data(
//                            "T Roof",
//                            R.drawable.abench_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
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
//                        Data(
//                            "Banana",
//                            R.drawable.in_ct_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "Barrels",
//                            R.drawable.in_barrels
//                        )
//                    )
//                }
//
//                1 -> {
//                    throwSpots.add(
//                        Data(
//                            "Banana",
//                            R.drawable.in_coffins_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "Banana Fast Land",
//                            R.drawable.in_coffins_spawn1
//                        )
//                    )
//                }
//
//                2 -> {
//                    throwSpots.add(
//                        Data(
//                            "Mid",
//                            R.drawable.moto_spawn0
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "Underpass",
//                            R.drawable.moto_spawn1
//                        )
//                    )
//                }
//                3 -> {
//                    throwSpots.add(
//                        Data(
//                            "Coffins",
//                            R.drawable.in_coffins
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "Ruins entrance",
//                            R.drawable.in_tree_bench
//                        )
//                    )
//                }
//
//                4 -> {
//                    throwSpots.add(
//                        Data(
//                            "Coffins",
//                            R.drawable.in_coffins
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "Second box",
//                            R.drawable.in_2nd_box
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
//                            "Triple",
//                            R.drawable.in_triple
//                        )
//                    )
//                    throwSpots.add(
//                        Data(
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