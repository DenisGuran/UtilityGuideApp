package com.example.csapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.csapp.Global.Companion.land
import com.example.csapp.Global.Companion.maps
import com.example.csapp.Global.Companion.selectedSmoke
import com.example.csapp.R
import com.example.csapp.adapters.UtilityAdapter
import com.example.csapp.databinding.FragmentSmokeBinding
import com.example.csapp.models.Data

class SmokeFragment : Fragment(R.layout.fragment_smoke) {

    private var _binding: FragmentSmokeBinding? = null
    private val binding get() = _binding!!
    private val landingSpots = ArrayList<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSmokeBinding.inflate(inflater, container, false)
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
                UtilityAdapter(it,landingSpots, object:UtilityAdapter.OnClickListener{
                    override fun onItemClick(position: Int) {
                        findNavController().navigate(R.id.nav_throw_pos)
                        selectedSmoke = position
                        land = landingSpots[position].name
                    }
                })
            }
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun initData(){

        val smokeLayout = binding.smokeLayout
        landingSpots.clear()

        if(maps["mirage"] == true) {

            smokeLayout.setBackgroundResource(R.drawable.mirage_background_blur)

            landingSpots.add(Data(
                "Stairs",
                R.drawable.stairs_smoke)
            )
            landingSpots.add(
                Data(
                "A Bench",
                R.drawable.bench_smoke)
            )
        }

        if(maps["inferno"] == true){

            smokeLayout.setBackgroundResource(R.drawable.inferno_background_blur)

            landingSpots.add(Data(
                "CT Spawn",
                R.drawable.in_ct_smoke)
            )
            landingSpots.add(Data(
                "Coffins",
                R.drawable.in_coffins_land)
            )
            landingSpots.add(Data(
                "Moto",
                R.drawable.moto_smoke)
            )
            landingSpots.add(Data(
                "Deep banana",
                R.drawable.in_deep_banana_land)
            )
            landingSpots.add(Data(
                "Pool",
                R.drawable.in_pool_land)
            )
        }

        if(maps["dust2"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.dust2_background_blur)
            landingSpots.add(Data(
                "Coming Soon",
                R.drawable.dust2_background)
            )
        }

        if(maps["overpass"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.overpass_background_blur)
            landingSpots.add(Data(
                "Coming Soon",
                R.drawable.overpass_background)
            )
        }

        if(maps["nuke"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.nuke_background_blur)
            landingSpots.add(Data(
                "Coming Soon",
                R.drawable.nuke_background)
            )
        }

        if(maps["vertigo"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.vertigo_background_blur)
            landingSpots.add(Data(
                "Coming Soon",
                R.drawable.vertigo_background)
            )
        }

        if(maps["ancient"] == true) {
            smokeLayout.setBackgroundResource(R.drawable.ancient_background_blur)
            landingSpots.add(Data(
                "Coming Soon",
                R.drawable.ancient_background)
            )
        }
    }

}