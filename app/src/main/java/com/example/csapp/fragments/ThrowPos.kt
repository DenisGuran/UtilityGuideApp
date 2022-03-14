package com.example.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csapp.Global.Companion.maps
import com.example.csapp.Global.Companion.pos
import com.example.csapp.Global.Companion.selectedPos
import com.example.csapp.Global.Companion.selectedSmoke
import com.example.csapp.R
import com.example.csapp.adapters.UtilityAdapter
import com.example.csapp.databinding.FragmentThrowPosBinding
import com.example.csapp.models.Data


class ThrowPos : Fragment(R.layout.fragment_throw_pos) {

    private var _binding: FragmentThrowPosBinding? = null
    private val binding get() = _binding!!
    private val throwSpots = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThrowPosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setRecyclerView()
    }

    private fun setRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this.activity)
        binding.recyclerView.adapter =
            context?.let {
                UtilityAdapter(it, throwSpots, object: UtilityAdapter.OnClickListener{
                    override fun onItemClick(position: Int) {
                        findNavController().navigate(R.id.nav_tutorial)
                        selectedPos = position
                        pos = throwSpots[position].name
                    }
                })
            }
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initData(){

        val throwPosLayout = binding.throwPosLayout

        throwSpots.clear()

        if(maps["mirage"] == true) {

            throwPosLayout.setBackgroundResource(R.drawable.mirage_background_blur)

            when(selectedSmoke){
                0 -> {
                    throwSpots.add(
                        Data(
                            "T Stairs",
                            R.drawable.stairs_spawn0
                        )
                    )
                    throwSpots.add(
                        Data(
                            "T Spawn",
                            R.drawable.stairs_spawn1
                        )
                    )
                }

                1 -> {
                    throwSpots.add(
                        Data(
                            "T Roof",
                            R.drawable.abench_spawn0
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Underground",
                            R.drawable.abench_spawn1
                        )
                    )
                }
            }
        }

        if(maps["inferno"] == true) {

            throwPosLayout.setBackgroundResource(R.drawable.inferno_background_blur)

            when(selectedSmoke) {
                0 ->{
                    throwSpots.add(
                        Data(
                            "Banana",
                            R.drawable.in_ct_spawn0
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Barrels",
                            R.drawable.in_barrels
                        )
                    )
                }

                1 ->{
                    throwSpots.add(
                        Data(
                            "Banana",
                            R.drawable.in_coffins_spawn0
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Banana Fast Land",
                            R.drawable.in_coffins_spawn1
                        )
                    )
                }

                2 -> {
                    throwSpots.add(
                        Data(
                            "Mid",
                            R.drawable.moto_spawn0
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Underpass",
                            R.drawable.moto_spawn1
                        )
                    )
                }
                3 -> {
                    throwSpots.add(
                        Data(
                            "Coffins",
                            R.drawable.in_coffins
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Ruins entrance",
                            R.drawable.in_tree_bench
                        )
                    )
                }

                4 -> {
                    throwSpots.add(
                        Data(
                            "Coffins",
                            R.drawable.in_coffins
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Second box",
                            R.drawable.in_2nd_box
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Triple",
                            R.drawable.in_triple
                        )
                    )
                    throwSpots.add(
                        Data(
                            "Construction",
                            R.drawable.in_construction
                        )
                    )
                }
            }

        }

    }

}